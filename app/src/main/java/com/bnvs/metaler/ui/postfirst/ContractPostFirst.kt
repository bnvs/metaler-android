package com.bnvs.metaler.ui.postfirst

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.postdetails.model.PostDetails

interface ContractPostFirst {
    interface View : BaseView<Presenter> {
        fun showCategories()
        fun setCategory(category: String)
        fun setTitle(title: String)
        fun setPrice(price: Int)
        fun setPriceType(priceType: String)
        fun setImageGuideText()
        fun setImages()
        fun showPostDetails(postDetails: PostDetails)
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
        fun setCategory(categoryId: Int)
        fun setTitle(title: String)
        fun setPrice(price: Int)
        fun setPriceType(priceType: String)
        fun setImage()
        fun setContents()
        fun getImageFromAlbum()
        fun getImageFromCamera()
        fun uploadImage()
        fun getAttachUrl()
        fun openPostSecond()
    }
}