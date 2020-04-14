package com.bnvs.metaler.data.addeditpost.source

import com.bnvs.metaler.data.addeditpost.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.AddPostResponse
import com.bnvs.metaler.data.addeditpost.DeletePostResponse
import com.bnvs.metaler.data.addeditpost.EditPostResponse

interface AddEditPostDataSource {

    fun addPost(
        request: AddEditPostRequest,
        onSuccess: (response: AddPostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun editPost(
        postId: Int,
        request: AddEditPostRequest,
        onSuccess: (response: EditPostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun deletePost(
        postId: Int,
        onSuccess: (response: DeletePostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}