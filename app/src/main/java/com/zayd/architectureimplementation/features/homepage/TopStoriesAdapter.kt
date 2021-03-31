package com.zayd.architectureimplementation.features.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zayd.architectureimplementation.R
import com.zayd.architectureimplementation.models.Article
import com.zayd.architectureimplementation.repository.topstories.TopStories
import com.zayd.architectureimplementation.utils.Extensions.getProperString

class TopStoriesAdapter(private val topStoriesInterface: TopStoriesInterface): ListAdapter<TopStories, TopStoriesAdapter.NewsViewHolder>(
    NewsDiffUtil()
) {

    interface TopStoriesInterface {
        fun openArticle(url: String, title: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position), topStoriesInterface)
    }

    class NewsViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val authorName = itemView.findViewById<TextView>(R.id.authorName)
        private val titleName = itemView.findViewById<TextView>(R.id.titleName)
        private val imageContainer = itemView.findViewById<ImageView>(R.id.imageContainer)
        private val contentText = itemView.findViewById<TextView>(R.id.contentText)
        private val openArticle = itemView.findViewById<TextView>(R.id.openArticle)
        companion object {
            fun from (parent: ViewGroup): NewsViewHolder =
                NewsViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_news, parent, false)
                )
        }

        fun bind(article: TopStories, topStoriesInterface: TopStoriesInterface) {
            authorName.text = article.author.getProperString("Author")
            titleName.text = article.title.getProperString("Title")
            contentText.text = article.content.getProperString("Content")
            Picasso.get().load(article.urlToImage).fit().error(R.drawable.ic_broken_image).into(imageContainer)
            openArticle.setOnClickListener {
                topStoriesInterface.openArticle(article.url, article.title)
            }
        }
    }
}

class NewsDiffUtil: DiffUtil.ItemCallback<TopStories>() {
    override fun areItemsTheSame(oldItem: TopStories, newItem: TopStories): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: TopStories, newItem: TopStories): Boolean {
        return oldItem == newItem
    }
}