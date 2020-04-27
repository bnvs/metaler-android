package com.bnvs.metaler.ui.postsecond

import android.content.Intent
import com.bnvs.metaler.data.addeditpost.model.AddEditPostRequest

class PresenterPostSecond(
    private val categoryType: String,
    private val postId: Int?,
    private val view: ContractPostSecond.View
) : ContractPostSecond.Presenter {

    private lateinit var addEditPostRequest: AddEditPostRequest

    override fun start() {
        distinguishMaterialOrManufacture()
        if (postId != null) {

        }
    }

    private fun distinguishMaterialOrManufacture() {
        when (categoryType) {
            "MATERIALS" -> {
                view.showManufactureWorkTagInput(false)
            }
            "MANUFACTURES" -> {
                view.showManufactureWorkTagInput(true)
            }
        }
    }

    override fun populatePost() {

    }

    override fun getAddEditPostRequest(intent: Intent) {
        addEditPostRequest = intent.getSerializableExtra("addEditPostRequest") as AddEditPostRequest
    }
}