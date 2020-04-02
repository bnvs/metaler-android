package com.bnvs.metaler.data.posts.source

import android.content.Context
import com.bnvs.metaler.data.posts.PostsRequest
import com.bnvs.metaler.data.posts.source.local.PostsLocalDataSource
import com.bnvs.metaler.data.posts.source.remote.PostsRemoteDataSource

class PostsRepository(context: Context) : PostsDataSource {

    private val postRemoteDataSource = PostsRemoteDataSource

    override fun getPosts(
        access_token: String,
        request: PostsRequest,
        callback: PostsDataSource.LoadPostsCallback
    ) {
        postRemoteDataSource.getPosts(access_token, request, callback)
    }
}