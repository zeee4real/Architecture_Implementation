package com.zayd.architectureimplementation.service

import com.zayd.architectureimplementation.models.TopHeadlinesModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    fun getTopStories(
        @Query("country") country: String = "in",
        @Query("apiKey") key: String = "586355e0c4014fe7b1d6cd762c39dca6"
    )
            : Single<TopHeadlinesModel>
}