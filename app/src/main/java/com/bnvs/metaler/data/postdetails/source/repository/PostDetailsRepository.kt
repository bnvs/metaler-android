package com.bnvs.metaler.data.postdetails.source.repository

import com.bnvs.metaler.data.postdetails.model.PostDetails
import com.bnvs.metaler.data.postdetails.model.RatingRequest
import com.bnvs.metaler.data.postdetails.source.PostDetailsDataSource
import com.bnvs.metaler.data.postdetails.source.remote.PostDetailsRemoteDataSource

class PostDetailsRepository :
    PostDetailsDataSource {

    private val postDetailsRemoteDataSource = PostDetailsRemoteDataSource

    override fun getPostDetails(
        postId: Int,
        onSuccess: (response: PostDetails) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        postDetailsRemoteDataSource.getPostDetails(postId, onSuccess, onFailure)
    }

    override fun deletePost(
        postId: Int,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        postDetailsRemoteDataSource.deletePost(postId, onSuccess, onFailure)
    }

    override fun ratePost(
        postId: Int,
        request: RatingRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        postDetailsRemoteDataSource.ratePost(postId, request, onSuccess, onFailure)
    }

    override fun unRatePost(
        postId: Int,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        postDetailsRemoteDataSource.unRatePost(postId, onSuccess, onFailure)
    }
}