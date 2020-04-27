package com.bnvs.metaler.ui.postsecond

import android.content.Intent
import com.bnvs.metaler.data.addeditpost.model.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.model.PostTag

class PresenterPostSecond(
    private val categoryType: String,
    private val postId: Int?,
    private val view: ContractPostSecond.View
) : ContractPostSecond.Presenter {

    private lateinit var addEditPostRequest: AddEditPostRequest
    private var shopNameTagString = ""
    private var workTagString = ""
    private var tagString = ""

    override fun start() {
        distinguishMaterialOrManufacture()
        if (postId != null) {
            getTagString(addEditPostRequest.tags)
            populatePost()
        }
    }

    override fun getAddEditPostRequest(intent: Intent) {
        addEditPostRequest = intent.getSerializableExtra("addEditPostRequest") as AddEditPostRequest
    }

    private fun distinguishMaterialOrManufacture() {
        when (categoryType) {
            "MATERIALS" -> {
                view.apply {
                    showManufactureWorkTagInput(false)
                    setShopNameTagInputAdapter()
                    setWorkTagInputAdapter()
                }
            }
            "MANUFACTURES" -> {
                view.apply {
                    showManufactureWorkTagInput(false)
                    setShopNameTagInputAdapter()
                    setWorkTagInputAdapter()
                    setWorkTagInputAdapter()
                }
            }
        }
    }

    override fun populatePost() {
        when (categoryType) {
            "MATERIALS" -> {
                setShopNameInput()
                setTagInput()
            }
            "MANUFACTURES" -> {
                setShopNameInput()
                setWorkInput()
                setTagInput()
            }
        }
    }

    override fun setShopNameInput() {
        view.setShopNameInput(shopNameTagString)
    }

    override fun setWorkInput() {
        view.setWorkInput(workTagString)
    }

    override fun setTagInput() {
        view.setTagInput(tagString)
    }

    private fun getTagString(tags: List<PostTag>) {
        for (tag in tags) {
            when (tag.type) {
                "store" -> shopNameTagString += "#${tag.name} "
                "work" -> workTagString += "#${tag.name} "
                "etc" -> tagString += "#${tag.name} "
            }
        }
    }
}