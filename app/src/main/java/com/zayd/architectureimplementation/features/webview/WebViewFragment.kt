package com.zayd.architectureimplementation.features.webview

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.zayd.architectureimplementation.R
import com.zayd.architectureimplementation.utils.Constants.EXTRA_WEB_VIEW_URL


class WebViewFragment: Fragment(R.layout.activity_webview) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url: String? = arguments?.getString(EXTRA_WEB_VIEW_URL)
        val webView: WebView = view.findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()

        url?.let {
            webView.loadUrl(url)
        }

    }

}