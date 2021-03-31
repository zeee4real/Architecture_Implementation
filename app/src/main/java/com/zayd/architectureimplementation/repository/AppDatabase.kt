package com.zayd.architectureimplementation.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zayd.architectureimplementation.repository.topstories.TopStories
import com.zayd.architectureimplementation.repository.topstories.TopStoriesDao
import com.zayd.architectureimplementation.utils.Constants

@Database(entities = [TopStories::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val topStoriesDao: TopStoriesDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, Constants.DATABASE_NAME
                    )
                        .build()
                }
            }
            return instance
        }
    }
}