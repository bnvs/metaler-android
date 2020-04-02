package com.bnvs.metaler.termsagree

import android.content.Intent
import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.user.AddUserRequest
import org.json.JSONObject

interface ContractTermsAgree {
    interface View : BaseView<Presenter> {
        fun showTermsWebView()

        fun showEssentialAgreeNotCheckedDialog()

        fun showJobInputUi(addUserRequest: AddUserRequest)
    }
    interface Presenter : BasePresenter {
        fun getAddUserRequest(intent: Intent)

        fun openTerms()

        fun openJobInput(result: JSONObject)
    }
}