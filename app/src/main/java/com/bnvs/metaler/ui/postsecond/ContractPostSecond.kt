package com.bnvs.metaler.ui.postsecond

import android.content.Intent
import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import org.json.JSONObject

interface ContractPostSecond {
    interface View : BaseView<Presenter> {
        fun showManufactureWorkTagInput(b: Boolean)
        fun setShopNameTagInputAdapter()
        fun setWorkTagInputAdapter()
        fun setTagInputAdapter()
        fun setShopNameInput(tags: String)
        fun setWorkInput(tags: String)
        fun setTagInput(tags: String)
        fun showEmptyTagsDialog()
        fun showInvalidateTagDialog()
        fun finishAddEditUi(categoryType: String)
        fun showAddPostFailureToast(errorMessage: String)
        fun showEditPostFailureToast(errorMessage: String)
    }

    interface Presenter : BasePresenter {
        fun getAddEditPostRequest(intent: Intent)
        fun populatePost()
        fun setShopNameInput()
        fun setWorkInput()
        fun setTagInput()
        fun finishAddEditPost(tags: JSONObject)
        fun completeAddEditPostRequest()
        fun addPost()
        fun editPost()
    }
}