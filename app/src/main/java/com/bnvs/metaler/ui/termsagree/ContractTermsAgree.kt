package com.bnvs.metaler.ui.termsagree

import android.content.Intent
import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.user.certification.model.AddUserRequest
import org.json.JSONObject

interface ContractTermsAgree {
    interface View : BaseView<Presenter> {
        fun showTermsWebView(url: String)

        fun showEssentialAgreeNotCheckedDialog()

        fun makeToast(message: String)

        fun showJobInputUi(addUserRequest: AddUserRequest)
    }
    interface Presenter : BasePresenter {
        fun getAddUserRequest(intent: Intent)

        fun getTerms()

        fun openTerms(index: Int)

        fun openJobInput(result: JSONObject)
    }
}