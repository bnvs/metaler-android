package com.bnvs.metaler_android.data.post.source.local

import android.content.Context
import com.bnvs.metaler_android.data.post.source.PostDataSource

class PostLocalDataSource(context: Context) : PostDataSource{

    private val sharedPreferences = context.getSharedPreferences("posts", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getPosts(callback: PostDataSource.LoadPostsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}