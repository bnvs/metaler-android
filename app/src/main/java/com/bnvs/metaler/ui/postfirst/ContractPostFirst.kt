package com.bnvs.metaler.ui.postfirst

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView

interface ContractPostFirst {
    interface View : BaseView<Presenter> {
        fun showCategories()
        fun showImageDeleteDialog()
        fun showEmptyTitleDialog()
        fun showEmptyPriceDialog()
        fun showEmptyPriceTypeDialog()
        fun showEmptyContentsDialog()
        fun showPostSecondUi()
    }

    interface Presenter : BasePresenter {
        fun getImageFromLocal()
        fun getImageFromCamera()
        fun uploadImage()
        fun getAttachUrl()
        fun openPostSecond()
    }
}