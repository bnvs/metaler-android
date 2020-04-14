package com.bnvs.metaler.data.addeditpost.source.local

import com.bnvs.metaler.data.addeditpost.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.AddPostResponse
import com.bnvs.metaler.data.addeditpost.DeletePostResponse
import com.bnvs.metaler.data.addeditpost.EditPostResponse
import com.bnvs.metaler.data.addeditpost.source.AddEditPostDataSource

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
}