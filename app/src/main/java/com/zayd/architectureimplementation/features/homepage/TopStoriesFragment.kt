package com.zayd.architectureimplementation.features.homepage

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.zayd.architectureimplementation.MainActivity.Companion.getFragmentUtil
import com.zayd.architectureimplementation.R
import com.zayd.architectureimplementation.features.webview.WebViewFragment
import com.zayd.architectureimplementation.repository.topstories.TopStories
import com.zayd.architectureimplementation.service.NetworkClient
import com.zayd.architectureimplementation.utils.AppUtils
import com.zayd.architectureimplementation.utils.Constants
import kotlinx.coroutines.*

class TopStoriesFragment : Fragment(R.layout.fragment_top_stories) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noData: TextView
    private lateinit var topStoriesAdapter: TopStoriesAdapter
    private var viewModel: TopStoriesViewModel? = null
    private lateinit var topStoriesFragmentInterface: TopStoriesFragmentInterface

    interface TopStoriesFragmentInterface {
        fun articleOpened(title: String)
    }

    fun setCallBack(topStoriesFragmentInterface: TopStoriesFragmentInterface) {
        this.topStoriesFragmentInterface = topStoriesFragmentInterface
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        noData = view.findViewById(R.id.noData)

        runBlocking {
            val job: Job = GlobalScope.launch {
                withContext(Dispatchers.Default) {
                    setupViewModel()
                }
            }
            job.join()

            viewModel?.topStories?.observe(viewLifecycleOwner, {
                initRecyclerView(it)
            })

            viewModel?.onError?.observe(viewLifecycleOwner, {
                if(it)
                    Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show()
            })
        }

    }

    private fun setupViewModel() {
        try {
            context?.let { context ->
                val viewModelFactory = TopStoriesViewModelFactory(NetworkClient.get(context), context)
                viewModel = ViewModelProvider(this, viewModelFactory)
                    .get(TopStoriesViewModel::class.java)
            }
        } catch (ex: IllegalArgumentException) {
            Log.e("TopStoriesFragment", "ViewModel Class Error", ex)
        }
    }

    private fun initRecyclerView(articles: List<TopStories>) {
        if (!articles.isNullOrEmpty()) {
            AppUtils.setViewVisible(noData, false)
            AppUtils.setViewVisible(recyclerView, true)
        }
        recyclerView.apply {
            topStoriesAdapter = TopStoriesAdapter(object : TopStoriesAdapter.TopStoriesInterface {
                override fun openArticle(url: String, title: String) {
                    val fragment = WebViewFragment()
                    val bundle = Bundle()
                    bundle.putString(Constants.EXTRA_WEB_VIEW_URL, url)
                    fragment.arguments = bundle
                    topStoriesFragmentInterface.articleOpened(title)
                    getFragmentUtil(this@TopStoriesFragment)?.addFragment(
                        activity, fragment, null, addToStack = true
                    )
                }
            })
            adapter = topStoriesAdapter
        }
        topStoriesAdapter.submitList(articles)
    }
}