package com.bnvs.metaler.data.posts.source.local

import android.content.Context
import com.bnvs.metaler.data.posts.PostsRequest
import com.bnvs.metaler.data.posts.PostsResponse
import com.bnvs.metaler.data.posts.source.PostsDataSource

class PostsLocalDataSource(context: Context) : PostsDataSource{

    private val sharedPreferences =
        context.getSharedPreferences("LOCAL_POSTS_DATA", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getPosts(
        request: PostsRequest,
        onSuccess: (response: PostsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }
}