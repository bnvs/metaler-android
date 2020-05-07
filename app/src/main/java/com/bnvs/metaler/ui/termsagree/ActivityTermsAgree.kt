package com.bnvs.metaler.ui.termsagree

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bnvs.metaler.R
import com.bnvs.metaler.data.user.certification.model.AddUserRequest
import com.bnvs.metaler.ui.jobinput.ActivityJobInput
import com.bnvs.metaler.ui.login.ActivityLogin
import kotlinx.android.synthetic.main.activity_terms_agree.*
import org.json.JSONObject

class ActivityTermsAgree : AppCompatActivity(),
    ContractTermsAgree.View {

    private val TAG = "ActivityTermsAgree"

    override lateinit var presenter: ContractTermsAgree.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_agree)

        // Create the presenter
        presenter = PresenterTermsAgree(
            this@ActivityTermsAgree
        )

        // Set up Buttons
        initClickListeners()

        presenter.apply {
            start()
            getAddUserRequest(intent)
        }
    }

    override fun showTermsWebView(url: String) {
        val webView = WebView(this).apply {
            loadUrl(url)
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view!!.loadUrl(url)
                    return true
                }
            }
        }
        AlertDialog.Builder(this@ActivityTermsAgree)
            .setTitle("Metaler")
            .setView(webView)
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun showEssentialAgreeNotCheckedDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.terms_alert))
                .setMessage(getString(R.string.essential_terms_guide))
                .show()
        }
    }

    override fun makeToast(message: String) {
        Toast.makeText(this@ActivityTermsAgree, message, Toast.LENGTH_LONG).show()
    }

    override fun restartApp() {
        finishAffinity()
        Intent(this, ActivityLogin::class.java).also {
            startActivity(it)
        }
    }

    override fun showJobInputUi(addUserRequest: AddUserRequest) {
        Intent(this, ActivityJobInput::class.java).apply {
            putExtra("addUserRequest", addUserRequest)
            startActivity(this)
        }
    }

    private fun initClickListeners() {
        setCheckBoxes()
        setMoreButtons()
        setNextButton()
    }

    private fun setNextButton() {
        nextBtn.setOnClickListener { presenter.openJobInput(checkAgrees()) }
    }

    private fun setCheckBoxes() {
        allCheckBtn.setOnClickListener { onCheckChanged(allCheckBtn) }
        firstCheckBtn.setOnClickListener { onCheckChanged(firstCheckBtn) }
        secondCheckBtn.setOnClickListener { onCheckChanged(secondCheckBtn) }
        thirdCheckBtn.setOnClickListener { onCheckChanged(thirdCheckBtn) }
        fourthCheckBtn.setOnClickListener { onCheckChanged(fourthCheckBtn) }
    }

    private fun setMoreButtons() {
        firstAgreeMoreBtn.setOnClickListener { presenter.openTerms(1) }
        secondAgreeMoreBtn.setOnClickListener { presenter.openTerms(2) }
        thirdAgreeMoreBtn.setOnClickListener { presenter.openTerms(3) }
        fourthAgreeMoreBtn.setOnClickListener { presenter.openTerms(4) }
    }

    private fun onCheckChanged(compoundButton: CompoundButton) {
        when (compoundButton.id) {
            R.id.allCheckBtn -> {
                if (allCheckBtn.isChecked) {
                    firstCheckBtn.isChecked = true
                    secondCheckBtn.isChecked = true
                    thirdCheckBtn.isChecked = true
                    fourthCheckBtn.isChecked = true
                } else {
                    firstCheckBtn.isChecked = false
                    secondCheckBtn.isChecked = false
                    thirdCheckBtn.isChecked = false
                    fourthCheckBtn.isChecked = false
                }
            }
            else -> {
                allCheckBtn.isChecked = (
                        firstCheckBtn.isChecked
                                && secondCheckBtn.isChecked
                                && thirdCheckBtn.isChecked
                                && fourthCheckBtn.isChecked)
            }
        }
    }

    private fun checkAgrees(): JSONObject {
        val result = JSONObject()
        return if (firstCheckBtn.isChecked && secondCheckBtn.isChecked) {
            result.run {
                put("valid", true)
                put("firstTerm", firstCheckBtn.isChecked)
                put("secondTerm", secondCheckBtn.isChecked)
                put("thirdTerm", thirdCheckBtn.isChecked)
                put("fourthTerm", fourthCheckBtn.isChecked)
            }
        } else {
            result.put("valid", false)
        }
    }
}
