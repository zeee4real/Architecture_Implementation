package com.zayd.architectureimplementation.utils

import android.app.AlertDialog
import android.content.Context
import android.os.Looper
import android.view.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Handler

object AppUtils {

    fun setViewVisible(view: View, visible: Boolean) {
        if (visible)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }

    fun showNetworkErrorDialog(context: Context) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle("Network Error")
                    .setMessage("Check your internet connection")
                    .setPositiveButton("OK") { _, _ ->
                    }
                alertDialog.show()
            }
        }
    }
}
