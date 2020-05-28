package com.bnvs.metaler.view.termsagree

import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.BindingAdapter
import com.bnvs.metaler.R

@BindingAdapter("setTermsAgreeWebViewListener")
fun setTermsAgreeWebViewListener(view: TextView, url: String?) {
    view.setOnClickListener {
        val webView = WebView(view.context).apply {
            loadUrl(url ?: "")
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view!!.loadUrl(url)
                    return true
                }
            }
        }
        AlertDialog.Builder(view.context)
            .setTitle(view.context.getString(R.string.app_name))
            .setView(webView)
            .setPositiveButton(view.context.getString(R.string.allow)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}