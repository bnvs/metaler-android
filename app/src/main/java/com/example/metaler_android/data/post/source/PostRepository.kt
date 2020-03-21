package com.example.metaler_android.data.post.source

import android.content.Context
import com.example.metaler_android.data.post.source.local.PostLocalDataSource
import com.example.metaler_android.data.post.source.remote.PostRemoteDataSource

class PostRepository(context: Context) : PostDataSource {

    val postLocalDataSource = PostLocalDataSource(context)
    val postRemoteDataSource = PostRemoteDataSource

    override fun getPosts(callback: PostDataSource.LoadPostsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}