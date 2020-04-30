package com.bnvs.metaler.data.addeditpost.source

import com.bnvs.metaler.data.addeditpost.model.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.model.AddPostResponse
import com.bnvs.metaler.data.addeditpost.model.UploadFileResponse
import okhttp3.MultipartBody

interface AddEditPostDataSource {

    fun addPost(
        request: AddEditPostRequest,
        onSuccess: (response: AddPostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun editPost(
        postId: Int,
        request: AddEditPostRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun uploadFile(
        file: MultipartBody.Part,
        onSuccess: (response: UploadFileResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}