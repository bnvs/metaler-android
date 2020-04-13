package com.bnvs.metaler.ui.postfirst

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView

interface ContractPostFirst {
    interface View : BaseView<Presenter> {
        fun showCategories()
        fun showImageDeleteDialog()
        fun showPostSecondUi()
    }

    interface Presenter : BasePresenter {
        fun getImageFromLocal()
        fun uploadImage()
        fun getAttachUrl()
        fun openPostSecond()
    }
}