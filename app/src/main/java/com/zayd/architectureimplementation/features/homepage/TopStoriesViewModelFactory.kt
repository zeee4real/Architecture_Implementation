package com.zayd.architectureimplementation.features.homepage

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zayd.architectureimplementation.service.ApiService

class TopStoriesViewModelFactory(private val apiService: ApiService?, private val context: Context)
    : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TopStoriesViewModel::class.java))
            return TopStoriesViewModel(apiService, context) as T
        throw IllegalArgumentException("Unknown View Model Class")
    }
}