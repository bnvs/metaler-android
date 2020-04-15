package com.bnvs.metaler.data.postdetails.source

import com.bnvs.metaler.data.postdetails.PostDetails

interface PostDetailsDataSource {

    fun getPostDetails(
        postId: Int,
        onSuccess: (response: PostDetails) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

}