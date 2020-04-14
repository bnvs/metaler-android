package com.bnvs.metaler.data.addeditpost.source.local

import com.bnvs.metaler.data.addeditpost.*
import com.bnvs.metaler.data.addeditpost.source.AddEditPostDataSource
import okhttp3.MultipartBody

class AddEditPostLocalDataSource : AddEditPostDataSource {
    override fun addPost(
        request: AddEditPostRequest,
        onSuccess: (response: AddPostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }

    override fun editPost(
        postId: Int,
        request: AddEditPostRequest,
        onSuccess: (response: EditPostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }

    override fun deletePost(
        postId: Int,
        onSuccess: (response: DeletePostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }

    override fun uploadFile(
        file: MultipartBody.Part,
        onSuccess: (response: UploadFileResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }
}