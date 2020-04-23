package com.bnvs.metaler.ui.postfirst

import android.content.Context
import android.content.Intent
import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.addeditpost.model.AddEditPostRequest
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
        fun openAlbum()
        fun openCamera()
        fun setImages(images: List<String>)
        fun addImage(imageUrl: String)
        fun deleteImage(imageIndex: Int)
        fun setContents(contents: String)
        fun showChooseCategory()
        fun showWhereToGetImageFromDialog()
        fun showUploadImageFailedToast(errorMessage: String)
        fun showPostDetailLoadFailedToast(errorMessage: String)
        fun showImageDeleteDialog(adapterPosition: Int)
        fun showEmptyCategory()
        fun showEmptyTitleDialog()
        fun showEmptyPriceDialog()
        fun showEmptyPriceTypeDialog()
        fun showEmptyContentsDialog()
        fun showPostSecondUi(addEditPostRequest: AddEditPostRequest)
        fun test(file: File)
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
        fun getImageFromAlbum(context: Context, data: Intent)
        fun getImageFromCamera(context: Context, data: Intent)
        fun uploadImage(file: File)
        fun getAttachUrl()
        fun setAddEditPostRequest()
        fun openPostSecond()
    }
}