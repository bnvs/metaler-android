package com.bnvs.metaler.data.postdetails.source

import com.bnvs.metaler.data.postdetails.model.PostDetails

interface PostDetailsDataSource {

    fun getPostDetails(
        postId: Int,
        onSuccess: (response: PostDetails) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun deletePost(
        postId: Int,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

}