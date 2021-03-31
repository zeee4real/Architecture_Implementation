package com.zayd.architectureimplementation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.google.android.material.card.MaterialCardView

class HomeActivity : AppCompatActivity() {

    private lateinit var view: MaterialCardView
    private lateinit var home: MotionLayout
    private var clicks = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        home = findViewById(R.id.home)
        view = findViewById(R.id.view)

        setClickListener()
    }

    private fun setClickListener() {
        view.setOnClickListener {
            when (clicks) {
                0 -> {
                    setTransition(home.currentState, R.id.end)
                    clicks += 1
                }
                1 -> {
                    setTransition(home.currentState, R.id.endnew)
                    clicks += 1
                    view.radius = 0f
                }
                2 -> {
                    startActivity(
                        Intent(
                            this, MainActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    )
                }
            }
        }
    }

    private fun setTransition(startState: Int, endState: Int) {
        home.setTransition(startState, endState)
        home.setTransitionDuration(500)
        home.transitionToEnd()
    }
}