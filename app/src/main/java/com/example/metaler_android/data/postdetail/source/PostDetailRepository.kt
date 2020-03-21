package com.example.metaler_android.data.postdetail.source

import android.content.Context
import com.example.metaler_android.data.postdetail.source.local.PostDetailsLocalDataSource
import com.example.metaler_android.data.postdetail.source.remote.PostDetailsRemoteDataSource

class PostDetailRepository(context: Context) : PostDetailDataSource {

    private val postDetailsLocalDataSource = PostDetailsLocalDataSource(context)
    private val postDetialsRemoteDataSource = PostDetailsRemoteDataSource

    override fun getPostDetails(callback: PostDetailDataSource.LoadPostDetailsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}