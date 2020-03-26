package com.bnvs.metaler.data.postdetails.source.local

import android.content.Context
import com.bnvs.metaler.data.postdetails.source.PostDetailsDataSource

class PostDetailsLocalDataSource(context: Context) : PostDetailsDataSource {

    private val sharedPreferences = context.getSharedPreferences("postDetails", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getPostDetails(callback: PostDetailsDataSource.LoadPostDetailsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}