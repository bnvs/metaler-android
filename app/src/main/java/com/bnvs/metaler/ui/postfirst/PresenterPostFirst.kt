package com.bnvs.metaler.ui.postfirst

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
        null,
        null
    )


    override fun start() {
        if (postId != 0) {
            populatePost(postId)
        }
        if (categoryType == "MATERIALS") {
            view.showCategories()
        }
    }

    override fun populatePost(postId: Int) {
        postDetailsRepository.getPostDetails(
            postId,
            onSuccess = { response ->
                view.showPostDetails(response)
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
        view.setPriceType(priceType)
    }

    override fun setImage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setContents() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getImageFromAlbum() {

    }

    override fun getImageFromCamera() {

    }

    override fun uploadImage() {

    }

    override fun getAttachUrl() {

    }

    override fun openPostSecond() {

    }
}