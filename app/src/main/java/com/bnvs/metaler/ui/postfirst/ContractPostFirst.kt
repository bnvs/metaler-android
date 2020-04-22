package com.bnvs.metaler.ui.postfirst

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView

interface ContractPostFirst {
    interface View : BaseView<Presenter> {
        fun showCategories()
        fun showPostDetailLoadFailedDialog(errorMessage: String)
        fun showImageDeleteDialog()
        fun showEmptyTitleDialog()
        fun showEmptyPriceDialog()
        fun showEmptyPriceTypeDialog()
        fun showEmptyContentsDialog()
        fun showPostSecondUi()
    }

    interface Presenter : BasePresenter {
        fun populatePost(postId: Int)
        fun getImageFromAlbum()
        fun getImageFromCamera()
        fun uploadImage()
        fun getAttachUrl()
        fun openPostSecond()
    }
}