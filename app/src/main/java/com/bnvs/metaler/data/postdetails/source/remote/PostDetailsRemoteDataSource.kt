package com.bnvs.metaler.data.postdetails.source.remote

import com.bnvs.metaler.data.postdetails.model.PostDetails
import com.bnvs.metaler.data.postdetails.model.RatingRequest

interface PostDetailsRemoteDataSource {
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

    fun ratePost(
        postId: Int,
        request: RatingRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun unRatePost(
        postId: Int,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}