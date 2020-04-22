package com.bnvs.metaler.ui.postfirst

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bnvs.metaler.data.addeditpost.model.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.source.repository.AddEditPostRepository
import com.bnvs.metaler.data.postdetails.source.repository.PostDetailsRepository
import com.bnvs.metaler.network.NetworkUtil

class PresenterPostFirst(
    private val categoryType: String,
    private val postId: Int,
    private val view: ContractPostFirst.View
) : ContractPostFirst.Presenter {

    private val addEditPostRepository = AddEditPostRepository()
    private val postDetailsRepository = PostDetailsRepository()

    private var addEditPostRequest = AddEditPostRequest(
        null,
        null,
        null,
        null,
        null,
        mutableListOf(),
        mutableListOf()
    )

    override fun start() {
        if (categoryType == "MATERIALS") {
            view.showCategories()
        }
        if (postId != 0) {
            populatePost(postId)
        }
    }

    override fun populatePost(postId: Int) {
        postDetailsRepository.getPostDetails(
            postId,
            onSuccess = { response ->
                setCategory(response.category_id)
                setTitle(response.title)
                setPrice(response.price)
                setPriceType(response.price_type)
                setImage(response.attach_ids, response.attach_urls)
                setContents(response.content)
            },
            onFailure = { e ->
                view.showPostDetailLoadFailedDialog(NetworkUtil.getErrorMessage(e))
            }
        )
    }

    override fun setCategory(categoryId: Int) {
        addEditPostRequest.category_id = categoryId
        if (categoryType == "MATERIALS") {
            val category = when (categoryId) {
                2 -> "적동/황동"
                3 -> "스테인리스"
                4 -> "알루미늄"
                5 -> "철"
                6 -> "공구"
                7 -> "화학 약품류"
                8 -> "니켈/티타늄"
                9 -> "기타"
                else -> return
            }
            view.setCategory(category)
        }
    }

    override fun setTitle(title: String) {
        addEditPostRequest.title = title
        view.setTitle(title)
    }

    override fun setPrice(price: Int) {
        addEditPostRequest.price = price
        view.setPrice(price)
    }

    override fun setPriceType(priceType: String) {
        addEditPostRequest.price_type = priceType
        when (priceType) {
            "card" -> view.setCardButton()
            "cash" -> view.setCashButton()
            else -> return
        }
    }

    override fun setImage(attachIds: List<Int>, attachUrls: List<String>) {
        addEditPostRequest.attach_ids = attachIds.toMutableList()
        if (attachUrls.isEmpty()) {
            view.setImageGuideText(true)
        } else {
            view.setImageGuideText(false)
            view.setImages(attachUrls)
        }
    }

    override fun addImage(attachId: Int, imageUrl: String) {
        addEditPostRequest.attach_ids.add(attachId)
        view.addImage(imageUrl)
    }

    override fun deleteImage(imageIndex: Int) {
        addEditPostRequest.attach_ids.removeAt(imageIndex)
        view.deleteImage(imageIndex)
    }

    override fun setContents(contents: String) {
        addEditPostRequest.content = contents
        view.setContents(contents)
    }

    override fun openWhereToGetImageFrom() {
        view.showWhereToGetImageFromDialog()
    }

    override fun getImageFromAlbumIntent(context: Context): Intent {
        return Intent(Intent.ACTION_GET_CONTENT).apply {
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "image/*"
        }
    }

    override fun getImageFromAlbum(context: Context, data: Intent) {
        val bitmaps = mutableListOf<Bitmap>()
        val clipData = data.clipData
        if (clipData != null) {
            for (i in 0..clipData.itemCount) {
                val imageUri = clipData.getItemAt(i).uri
                val inputStream = context.contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                bitmaps.add(bitmap)
            }
        } else {
            val imageUri = data.data
            if (imageUri != null) {
                val inputStream = context.contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                bitmaps.add(bitmap)
            }
        }
    }

    override fun getImageFromCamera() {

    }

    override fun uploadImage() {

    }

    override fun getAttachUrl() {

    }

    override fun openPostSecond() {
        view.showPostSecondUi()
    }
}