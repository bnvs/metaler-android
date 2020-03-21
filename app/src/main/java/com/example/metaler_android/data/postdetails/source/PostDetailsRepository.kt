package com.example.metaler_android.data.postdetails.source

import android.content.Context
import com.example.metaler_android.data.postdetails.source.local.PostDetailsLocalDataSource
import com.example.metaler_android.data.postdetails.source.remote.PostDetailsRemoteDataSource

class PostDetailsRepository(context: Context) : PostDetailsDataSource {

    private val postDetailsLocalDataSource = PostDetailsLocalDataSource(context)
    private val postDetialsRemoteDataSource = PostDetailsRemoteDataSource

    override fun getPostDetails(callback: PostDetailsDataSource.LoadPostDetailsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}