package com.zayd.architectureimplementation.repository.topstories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zayd.architectureimplementation.models.Article


@Entity(tableName = "top_stories_table")
data class TopStories(
    @ColumnInfo(name = "author") val author: String? = "",
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "publishedAt") val publishedAt: String,
    @ColumnInfo(name = "title") val title: String,
    @PrimaryKey @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "urlToImage") val urlToImage: String
) {
    constructor(author: Article) : this(
            author.author,
            author.content,
            author.description,
            author.publishedAt,
            author.title,
            author.url,
            author.urlToImage
    )
}