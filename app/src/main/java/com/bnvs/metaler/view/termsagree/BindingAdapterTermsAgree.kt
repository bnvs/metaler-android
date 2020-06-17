package com.bnvs.metaler.view.termsagree

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.BindingAdapter
import com.bnvs.metaler.R
import com.bnvs.metaler.util.setOnSingleClickListener

@BindingAdapter("setTermsAgreeWebViewListener")
fun setTermsAgreeWebViewListener(view: TextView, url: String?) {
    view.setOnSingleClickListener(
        View.OnClickListener {
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
                .setPositiveButton(view.context.getString(R.string.close)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

    )
}