package com.bnvs.metaler.termsagree

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import org.json.JSONObject

interface ContractTermsAgree {
    interface View : BaseView<Presenter> {
        fun showTermsWebView()

        fun showEssentialAgreeNotCheckedDialog()

        fun showJobInputUi(result: JSONObject)
    }
    interface Presenter : BasePresenter {
        fun openTerms()

        fun openJobInput(result: JSONObject)
    }
}