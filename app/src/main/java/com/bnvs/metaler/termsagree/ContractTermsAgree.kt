package com.bnvs.metaler.termsagree

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView

interface ContractTermsAgree {
    interface View : BaseView<Presenter> {
        fun showTermsWebView()

        fun showEssentialAgreeNotCheckedDialog()

        fun showJobInputUi()
    }
    interface Presenter : BasePresenter {

        fun openTerms()

        fun isEssentialAgreeChecked() : Boolean

        fun openJobInput()
    }
}