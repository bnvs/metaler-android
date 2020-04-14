package com.bnvs.metaler.data.posts.source.repository

import android.content.Context
import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsResponse
import com.bnvs.metaler.data.posts.source.PostsDataSource
import com.bnvs.metaler.data.posts.source.remote.PostsRemoteDataSource

class PostsRepository(context: Context) : PostsDataSource {

    private val postRemoteDataSource = PostsRemoteDataSource

    override fun getPosts(
        request: PostsRequest,
        onSuccess: (response: PostsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        postRemoteDataSource.getPosts(request, onSuccess, onFailure)
    }
}