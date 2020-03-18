package com.example.metaler_android.data.post.source.local

import com.example.metaler_android.data.post.source.PostDataSource
import com.example.metaler_android.network.RetrofitClient

class PostLocalDataSource : PostDataSource{

    private val retrofitClient = RetrofitClient.client

    override fun getPosts(callback: PostDataSource.LoadPostsCallback) {
        retrofitClient.getMaterialPosts()
    }

}