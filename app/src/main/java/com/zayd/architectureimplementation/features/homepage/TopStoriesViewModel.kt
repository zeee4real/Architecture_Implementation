package com.zayd.architectureimplementation.features.homepage

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zayd.architectureimplementation.features.homepage.TopStoriesRepository
import com.zayd.architectureimplementation.models.Article
import com.zayd.architectureimplementation.models.TopHeadlinesModel
import com.zayd.architectureimplementation.repository.AppDatabase
import com.zayd.architectureimplementation.repository.topstories.TopStories
import com.zayd.architectureimplementation.repository.topstories.TopStoriesDbRepository
import com.zayd.architectureimplementation.service.ApiService
import kotlinx.coroutines.*

class TopStoriesViewModel(private val apiService: ApiService?, context: Context) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val topStoriesDbRepository: TopStoriesDbRepository
    var topStories: LiveData<List<TopStories>>? = null
    private var _onError = MutableLiveData<Boolean>()
    val onError: LiveData<Boolean>
        get() = _onError


    init {
        getTopStories()
        val databaseInstance: AppDatabase = AppDatabase.getInstance(context)!!
        topStoriesDbRepository = TopStoriesDbRepository(databaseInstance.topStoriesDao)
        topStories = topStoriesDbRepository.getAllTopStories()
    }


    private fun getTopStories() {
        apiService?.let {
            TopStoriesRepository(it).getTopStories(object :
                TopStoriesRepository.NewsRepositoryInterface {
                override fun onApiSuccess(response: TopHeadlinesModel) {
                    extractAndInsertData(response.articles)
                    _onError.value = false
                }

                override fun onError() {
                    _onError.value = true
                }
            })
        }
    }

    private fun extractAndInsertData(articles: List<Article>?) {
        articles?.let { listArticles ->
            uiScope.launch {
                withContext(Dispatchers.Default) {
                    listArticles.forEach {
                        insertTopStory(TopStories(it))
                    }
                }
            }
        }
    }

    private fun insertTopStory(item: TopStories) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val story: TopStories? = topStoriesDbRepository.getStory(item.url)
                if (story == null)
                    topStoriesDbRepository.insertTopStories(item)
                else {
                    if (story.publishedAt != item.publishedAt)
                        topStoriesDbRepository.insertAndReplaceStory(item)
                }
            }
        }
    }
}