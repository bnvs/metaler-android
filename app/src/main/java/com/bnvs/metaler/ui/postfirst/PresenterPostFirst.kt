package com.bnvs.metaler.ui.postfirst

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