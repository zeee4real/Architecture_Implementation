package com.zayd.architectureimplementation.features.homepage

import com.zayd.architectureimplementation.models.TopHeadlinesModel
import com.zayd.architectureimplementation.service.ApiService
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TopStoriesRepository(private val apiService: ApiService) {

    interface NewsRepositoryInterface {
        fun onApiSuccess(response: TopHeadlinesModel)
        fun onError()
    }

    fun getTopStories(callback: NewsRepositoryInterface) {
        apiService.getTopStories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<TopHeadlinesModel> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(t: TopHeadlinesModel) {
                    callback.onApiSuccess(t)
                }

                override fun onError(e: Throwable) {
                    callback.onError()
                }
            })
    }
}