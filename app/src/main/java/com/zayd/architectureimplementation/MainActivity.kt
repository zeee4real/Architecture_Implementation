package com.zayd.architectureimplementation

import android.os.Bundle
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import com.zayd.architectureimplementation.features.homepage.TopStoriesFragment
import com.zayd.architectureimplementation.utils.AppUtils
import com.zayd.architectureimplementation.utils.Extensions.getProperString
import com.zayd.architectureimplementation.utils.FragmentUtils

class MainActivity : AppCompatActivity() {

    companion object {
        private val fragmentUtils = FragmentUtils()
        fun getFragmentUtil(fragment: Fragment): FragmentUtils? {
            if(fragment is TopStoriesFragment)
                return fragmentUtils
            return null
        }
    }

    private lateinit var appBarCard: MaterialCardView
    private lateinit var titleTV: TextView
    private lateinit var articleTitle: TextView
    private lateinit var scrollView: NestedScrollView
    private var scrollPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val closeIV = findViewById<ImageView>(R.id.close)
        titleTV = findViewById(R.id.title)
        articleTitle = findViewById(R.id.articleTitle)
        scrollView = findViewById(R.id.scrollView)
        appBarCard = findViewById(R.id.appBarCard)
        val topStoriesFragment = TopStoriesFragment()

        fragmentUtils.addFragment(this, topStoriesFragment,null, addToStack = false)

        topStoriesFragment.setCallBack(object : TopStoriesFragment.TopStoriesFragmentInterface{
            override fun articleOpened(title: String) {
                articleTitle.text = title.getProperString("Title of the news")
                changeAppBarView(true)
                scrollPosition = scrollView.scrollY
                scrollView.fullScroll(ScrollView.FOCUS_UP)
            }
        })

        closeIV.setOnClickListener {
            backClicked()
        }

    }

    private fun backClicked() {
        changeAppBarView(false)
        fragmentUtils.popFragment(this)
        scrollView.scrollY = scrollPosition
    }

    override fun onBackPressed() {
        if(fragmentUtils.getBackStackCount(this) > 0)
            backClicked()
        else
            finish()
    }

    fun changeAppBarView(isArticleOpened: Boolean) {
        AppUtils.setViewVisible(titleTV, !isArticleOpened)
        AppUtils.setViewVisible(articleTitle, isArticleOpened)
        if(isArticleOpened){
            appBarCard.radius = 0f
        } else {
            appBarCard.radius = 20f
        }
    }

}