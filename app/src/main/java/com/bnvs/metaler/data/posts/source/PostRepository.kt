package com.bnvs.metaler.data.posts.source

import android.content.Context
import com.bnvs.metaler.data.posts.PostsRequest
import com.bnvs.metaler.data.posts.source.local.PostLocalDataSource
import com.bnvs.metaler.data.posts.source.remote.PostRemoteDataSource

class PostRepository(context: Context) : PostDataSource {

    private val postLocalDataSource = PostLocalDataSource(context)
    private val postRemoteDataSource = PostRemoteDataSource

    override fun getPosts(
        access_token: String,
        request: PostsRequest,
        callback: PostDataSource.LoadPostsCallback
    ) {
        postRemoteDataSource.getPosts(access_token, request, callback)
    }
}