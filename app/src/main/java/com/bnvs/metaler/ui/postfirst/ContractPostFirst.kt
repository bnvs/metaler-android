package com.bnvs.metaler.ui.postfirst

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView

interface ContractPostFirst {
    interface View : BaseView<Presenter> {
        fun showCategories()
        fun setCategory(category: String)
        fun setTitle(title: String)
        fun setPrice(price: Int)
        fun setCardButton()
        fun setCashButton()
        fun setImageGuideText(b: Boolean)
        fun setImages(images: List<String>)
        fun setContents(contents: String)
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
        fun setImage(attachIds: List<Int>, attachUrls: List<String>)
        fun setContents(contents: String)
        fun getImageFromAlbum()
        fun getImageFromCamera()
        fun uploadImage()
        fun getAttachUrl()
        fun openPostSecond()
    }
}