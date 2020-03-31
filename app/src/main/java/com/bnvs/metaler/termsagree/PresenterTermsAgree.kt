package com.bnvs.metaler.termsagree

import android.widget.CheckBox
import org.json.JSONObject

class PresenterTermsAgree(
    private val view: ContractTermsAgree.View
) : ContractTermsAgree.Presenter {

    override fun start() {  }

    override fun openTerms() {
        view.showTermsWebView()
    }

    override fun openJobInput(result: JSONObject) {
        if (result.getBoolean("valid")) {
            result.remove("valid")
            view.showJobInputUi(result)
        }else {
            view.showEssentialAgreeNotCheckedDialog()
        }
    }
}