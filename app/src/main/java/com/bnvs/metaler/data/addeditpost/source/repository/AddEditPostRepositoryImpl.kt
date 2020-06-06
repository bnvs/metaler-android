package com.bnvs.metaler.data.addeditpost.source.repository

import com.bnvs.metaler.data.addeditpost.model.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.model.AddPostResponse
import com.bnvs.metaler.data.addeditpost.model.UploadFileResponse
import com.bnvs.metaler.data.addeditpost.source.local.AddEditPostLocalDataSource
import com.bnvs.metaler.data.addeditpost.source.remote.AddEditPostRemoteDataSource
import okhttp3.MultipartBody

class AddEditPostRepositoryImpl(
    private val addEditPostLocalDataSource: AddEditPostLocalDataSource,
    private val addEditPostRemoteDataSource: AddEditPostRemoteDataSource
) : AddEditPostRepository {

    override fun addPost(
        request: AddEditPostRequest,
        onSuccess: (response: AddPostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        addEditPostRemoteDataSource.addPost(request, onSuccess, onFailure)
    }

    override fun editPost(
        postId: Int,
        request: AddEditPostRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        addEditPostRemoteDataSource.editPost(postId, request, onSuccess, onFailure)
    }

    override fun uploadFile(
        file: MultipartBody.Part,
        onSuccess: (response: UploadFileResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        addEditPostRemoteDataSource.uploadFile(file, onSuccess, onFailure)
    }
}