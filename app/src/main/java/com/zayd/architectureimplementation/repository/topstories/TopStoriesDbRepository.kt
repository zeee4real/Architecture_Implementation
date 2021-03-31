package com.zayd.architectureimplementation.repository.topstories

import androidx.lifecycle.LiveData

class TopStoriesDbRepository(private val topStoriesDao: TopStoriesDao) {

    fun insertTopStories(item: TopStories): Long =
        topStoriesDao.insertIntoTopStoriesTable(item)

    fun getAllTopStories(): LiveData<List<TopStories>> =
        topStoriesDao.getAllTopStories()

    fun insertAndReplaceStory(item: TopStories): Long =
        topStoriesDao.insertIntoTopStoriesTableAndReplace(item)

    fun getStory(url:String): TopStories =
        topStoriesDao.getStory(url)

}