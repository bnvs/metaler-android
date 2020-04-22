package com.bnvs.metaler.ui.postfirst

import android.content.Context
import android.content.Intent
import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import java.io.File

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
        fun addImage(imageUrl: String)
        fun deleteImage(imageIndex: Int)
        fun setContents(contents: String)
        fun showWhereToGetImageFromDialog()
        fun showUploadImageFailedDialog(errorMessage: String)
        fun showPostDetailLoadFailedDialog(errorMessage: String)
        fun showImageDeleteDialog(adapterPosition: Int)
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
        fun addImage(attachId: Int, imageUrl: String)
        fun deleteImage(imageIndex: Int)
        fun setContents(contents: String)
        fun openWhereToGetImageFrom()
        fun getImageFromAlbumIntent(context: Context): Intent
        fun getImageFromAlbum(data: Intent)
        fun getImageFromCamera()
        fun uploadImage(file: File)
        fun getAttachUrl()
        fun openPostSecond()
    }
}