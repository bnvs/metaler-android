package com.bnvs.metaler.view.termscheck

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bnvs.metaler.R
import com.bnvs.metaler.data.user.modification.model.Terms
import com.bnvs.metaler.data.user.modification.source.repository.UserModificationRepository
import com.bnvs.metaler.network.NetworkUtil
import kotlinx.android.synthetic.main.activity_terms_check.*

class ActivityTermsCheck : AppCompatActivity() {

    private lateinit var userModificationRepository: UserModificationRepository
    private lateinit var terms: Terms

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_check)

        userModificationRepository =
            UserModificationRepository()
        getTermLinks()
    }

    private fun getTermLinks() {
        userModificationRepository.getTerms(
            onSuccess = { response ->
                terms = response
                setClickListeners()
            },
            onFailure = { e ->
                makeToast(NetworkUtil.getErrorMessage(e))
                finish()
            }
        )
    }

    private fun setClickListeners() {
        backBtn.setOnClickListener { finish() }
        firstTermMoreBtn.setOnClickListener { showTermsWebView(terms.service) }
        secondTermMoreBtn.setOnClickListener { showTermsWebView(terms.privacy) }
        thirdTermMoreBtn.setOnClickListener { showTermsWebView(terms.additional) }
        fourthTermMoreBtn.setOnClickListener { showTermsWebView(terms.advertising) }
    }

    private fun showTermsWebView(url: String) {
        val webView = WebView(this).apply {
            loadUrl(url)
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view!!.loadUrl(url)
                    return true
                }
            }
        }
        AlertDialog.Builder(this@ActivityTermsCheck)
            .setTitle("Metaler")
            .setView(webView)
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun makeToast(message: String) {
        Toast.makeText(this@ActivityTermsCheck, message, Toast.LENGTH_LONG).show()
    }
}