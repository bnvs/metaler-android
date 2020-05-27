package com.bnvs.metaler.view.termsagree

import android.content.Intent
import com.bnvs.metaler.data.user.certification.model.AddUserRequest
import com.bnvs.metaler.data.user.modification.model.Terms
import com.bnvs.metaler.data.user.modification.source.repository.UserModificationRepository
import com.bnvs.metaler.network.NetworkUtil
import org.json.JSONObject

class PresenterTermsAgree(
    private val view: ContractTermsAgree.View
) : ContractTermsAgree.Presenter {

    private lateinit var addUserRequest: AddUserRequest
    private val userModificationRepository =
        UserModificationRepository()
    private lateinit var terms: Terms

    override fun start() {
        getTerms()
    }

    override fun getAddUserRequest(intent: Intent) {
        addUserRequest = intent.getSerializableExtra("addUserRequest") as AddUserRequest
    }

    override fun getTerms() {
        userModificationRepository.getTerms(
            onSuccess = { response ->
                terms = response
            },
            onFailure = { e ->
                view.run {
                    makeToast(NetworkUtil.getErrorMessage(e))
                }
            }
        )
    }

    override fun openTerms(index: Int) {
        when (index) {
            1 -> view.showTermsWebView(terms.service)
            2 -> view.showTermsWebView(terms.privacy)
            3 -> view.showTermsWebView(terms.additional)
            4 -> view.showTermsWebView(terms.advertising)
            else -> return
        }

    }

    override fun openJobInput(result: JSONObject) {
        if (result.getBoolean("valid")) {
            if (result["fourthTerm"] == true) {
                addUserRequest.push_allowed = 1
                view.showJobInputUi(addUserRequest)
            } else {
                addUserRequest.push_allowed = 0
                view.showJobInputUi(addUserRequest)
            }
        } else {
            view.showEssentialAgreeNotCheckedDialog()
        }
    }
}