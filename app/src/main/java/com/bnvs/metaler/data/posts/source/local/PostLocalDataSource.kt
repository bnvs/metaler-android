package com.bnvs.metaler.data.posts.source.local

import android.content.Context
import com.bnvs.metaler.data.posts.PostsRequest
import com.bnvs.metaler.data.posts.source.PostDataSource

class PostLocalDataSource(context: Context) : PostDataSource{

    private val sharedPreferences = context.getSharedPreferences("posts", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getPosts(
        access_token: String,
        request: PostsRequest,
        callback: PostDataSource.LoadPostsCallback
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}