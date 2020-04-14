package com.bnvs.metaler.data.postdetails.source

import android.content.Context
import com.bnvs.metaler.data.postdetails.PostDetails
import com.bnvs.metaler.data.postdetails.source.remote.PostDetailsRemoteDataSource

class PostDetailsRepository(context: Context) : PostDetailsDataSource {

    private val postDetialsRemoteDataSource = PostDetailsRemoteDataSource

    override fun getPostDetails(
        postId: Int,
        onSuccess: (response: PostDetails) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        postDetialsRemoteDataSource.getPostDetails(postId, onSuccess, onFailure)
    }
}