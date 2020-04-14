package com.bnvs.metaler.data.addeditpost.source

import com.bnvs.metaler.data.addeditpost.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.AddPostResponse
import com.bnvs.metaler.data.addeditpost.DeletePostResponse
import com.bnvs.metaler.data.addeditpost.EditPostResponse
import com.bnvs.metaler.data.addeditpost.source.remote.AddEditPostRemoteDataSource

class AddEditPostRepository : AddEditPostDataSource {

    private val addeditPostRemoteDataSource = AddEditPostRemoteDataSource

    override fun addPost(
        request: AddEditPostRequest,
        onSuccess: (response: AddPostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        addeditPostRemoteDataSource.addPost(request, onSuccess, onFailure)
    }

    override fun editPost(
        postId: Int,
        request: AddEditPostRequest,
        onSuccess: (response: EditPostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        addeditPostRemoteDataSource.editPost(postId, request, onSuccess, onFailure)
    }

    override fun deletePost(
        postId: Int,
        onSuccess: (response: DeletePostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        addeditPostRemoteDataSource.deletePost(postId, onSuccess, onFailure)
    }
}