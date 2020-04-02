package com.bnvs.metaler.data.posts.source.local

import android.content.Context
import com.bnvs.metaler.data.posts.PostsRequest
import com.bnvs.metaler.data.posts.source.PostsDataSource

class PostsLocalDataSource(context: Context) : PostsDataSource{

    private val sharedPreferences = context.getSharedPreferences("posts", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getPosts(
        access_token: String,
        request: PostsRequest,
        callback: PostsDataSource.LoadPostsCallback
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}