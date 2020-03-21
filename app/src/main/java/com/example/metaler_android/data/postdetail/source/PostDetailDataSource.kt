package com.example.metaler_android.data.postdetail.source

import com.example.metaler_android.data.postdetail.PostDetails

interface PostDetailDataSource {

    interface LoadPostDetailsCallback {
        fun onPostDetailsLoaded(postDetails: PostDetails)
        fun onDataNotAvailable()
    }

    fun getPostDetails(callback: LoadPostDetailsCallback)

}