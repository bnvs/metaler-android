package com.bnvs.metaler.termsagree

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView

interface ContractTermsAgree {
    interface View : BaseView<Presenter> {
        fun showTermsWebView()

        fun enableCheckButton()

        fun disableCheckButton()

        fun showAgreeCheckNotAvailableDialog()

        fun showJobInputUi()
    }
    interface Presenter : BasePresenter {
        fun agreeAll()

        fun agree()

        fun openTerms()

        fun checkAgreeAvailable()

        fun openJobInput()
    }
}