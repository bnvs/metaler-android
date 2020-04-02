package com.bnvs.metaler.termsagree

import android.content.Intent
import com.bnvs.metaler.data.user.AddUserRequest
import org.json.JSONObject

class PresenterTermsAgree(
    private val view: ContractTermsAgree.View
) : ContractTermsAgree.Presenter {

    private lateinit var addUserRequest: AddUserRequest

    override fun start() {  }

    override fun getAddUserRequest(intent: Intent) {
        addUserRequest = intent.getSerializableExtra("addUserRequest") as AddUserRequest
    }

    override fun openTerms() {
        view.showTermsWebView()
    }

    override fun openJobInput(result: JSONObject) {
        if (result.getBoolean("valid")) {
            if (result["fourthTerm"] == true) {
                addUserRequest.push_allowed = 1
                view.showJobInputUi(addUserRequest)
            }
        }else {
            view.showEssentialAgreeNotCheckedDialog()
        }
    }
}