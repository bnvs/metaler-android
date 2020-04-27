package com.bnvs.metaler.ui.postsecond

import android.content.Intent
import android.util.Log
import com.bnvs.metaler.data.addeditpost.model.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.model.PostTag
import org.json.JSONObject
import java.util.regex.Pattern

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

    override fun finishAddEditPost(tags: JSONObject) {
        setTagString(tags)
        if (tagValidation()) {
            completeAddEditPostRequest()
            if (postId != null) {
                editPost()
            } else {
                addPost()
            }
        } else {
            return
        }
    }

    override fun completeAddEditPostRequest() {
        when (categoryType) {
            "MATERIALS" -> {
                addEditPostRequest.tags.apply {
                    clear()
                    addAll(getTagList("store", shopNameTagString))
                    addAll(getTagList("etc", tagString))
                }
            }
            "MANUFACTURES" -> {
                addEditPostRequest.tags.apply {
                    clear()
                    addAll(getTagList("store", shopNameTagString))
                    addAll(getTagList("work", workTagString))
                    addAll(getTagList("etc", tagString))
                }
            }
        }
    }

    override fun addPost() {
        Log.d("addPost : 게시물 추가 완료", addEditPostRequest.toString())
    }

    override fun editPost() {
        Log.d("editPost : 게시물 수정 완료", addEditPostRequest.toString())
    }

    private fun setTagString(tags: JSONObject) {
        when (categoryType) {
            "MATERIALS" -> {
                shopNameTagString = tags.getString("store")
                tagString = tags.getString("etc")
            }
            "MANUFACTURES" -> {
                shopNameTagString = tags.getString("store")
                workTagString = tags.getString("work")
                tagString = tags.getString("etc")
            }
        }
    }

    private fun tagValidation(): Boolean {
        when (categoryType) {
            "MATERIALS" -> {
                if (shopNameTagString.isBlank() || shopNameTagString == "#") {
                    view.showEmptyTagsDialog()
                    return false
                }
                if (!tagStringValidation(shopNameTagString)) {
                    view.showInvalidateTagDialog()
                    return false
                }
                if (!tagStringValidation(tagString)) {
                    view.showInvalidateTagDialog()
                    return false
                }
                return true
            }
            "MANUFACTURES" -> {
                if (shopNameTagString.isBlank() || shopNameTagString == "#") {
                    view.showEmptyTagsDialog()
                    return false
                }
                if (workTagString.isBlank() || workTagString == "#") {
                    view.showEmptyTagsDialog()
                    return false
                }
                if (!tagStringValidation(shopNameTagString)) {
                    view.showInvalidateTagDialog()
                    return false
                }
                if (!tagStringValidation(workTagString)) {
                    view.showInvalidateTagDialog()
                    return false
                }
                if (!tagStringValidation(tagString)) {
                    view.showInvalidateTagDialog()
                    return false
                }
                return true
            }
            else -> return false
        }
    }

    private fun tagStringValidation(tagString: String): Boolean {
        val pattern = Pattern.compile("#([0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣]*)")
        if (tagString.isNotBlank()) {
            val tags = tagString.split("\\s".toRegex())
            for (tag in tags) {
                if (!pattern.matcher(tag).find()) {
                    return false
                }
            }
            return true
        } else {
            return true
        }
    }

    private fun getTagList(type: String, tagString: String): List<PostTag> {
        val tagList = mutableListOf<PostTag>()
        if (tagString.isNotBlank()) {
            val tags = tagString.split("\\s".toRegex())
            for (tag in tags) {
                if (tag != "#") {
                    tagList.add(PostTag(type, tag.replace("#", "")))
                }
            }
        }
        return tagList
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