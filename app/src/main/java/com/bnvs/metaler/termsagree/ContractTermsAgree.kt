package com.bnvs.metaler.termsagree

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView

interface ContractTermsAgree {
    interface View : BaseView<Presenter> {
        fun showTermsWebView()

        fun enableCheckButton()

        fun disableCheckButton()

        fun showAgreeTermsDialog()
    }
    interface Presenter : BasePresenter {
        fun clickAgreeButton()

        fun clickAgreeMoreButton()

        fun clickNextButton()
    }
}