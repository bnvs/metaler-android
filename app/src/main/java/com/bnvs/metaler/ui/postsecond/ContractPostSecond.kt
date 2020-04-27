package com.bnvs.metaler.ui.postsecond

import android.content.Intent
import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView

interface ContractPostSecond {
    interface View : BaseView<Presenter> {
        fun showManufactureWorkTagInput(b: Boolean)
        fun setShopNameTagInputAdapter()
        fun setWorkTagInputAdapter()
        fun setTagInputAdapter()
        fun setShopNameInput(tags: String)
        fun setWorkInput(tags: String)
        fun setTagInput(tags: String)
    }

    interface Presenter : BasePresenter {
        fun getAddEditPostRequest(intent: Intent)
        fun populatePost()
        fun setShopNameInput()
        fun setWorkInput()
        fun setTagInput()
    }
}