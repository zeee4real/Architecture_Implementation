package com.zayd.architectureimplementation.repository.topstories

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TopStoriesDao {

    @Query("SELECT * FROM top_stories_table")
    fun getAllTopStories(): LiveData<List<TopStories>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIntoTopStoriesTable(@NonNull item: TopStories): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIntoTopStoriesTableAndReplace(@NonNull item: TopStories): Long

    @Query("SELECT * FROM top_stories_table WHERE url=:url")
    fun getStory(@NonNull url: String): TopStories

}