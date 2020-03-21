package com.example.metaler_android.data.postdetail.source.local

import android.content.Context
import com.example.metaler_android.data.postdetail.source.PostDetailDataSource

class PostDetailsLocalDataSource(context: Context) : PostDetailDataSource {

    private val sharedPreferences = context.getSharedPreferences("postDetails", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getPostDetails(callback: PostDetailDataSource.LoadPostDetailsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}