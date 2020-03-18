package com.example.metaler_android.data.PostDetail.source

import com.example.metaler_android.data.PostDetail.PostDetails

interface PostDetailDataSource {

    interface LoadPostDetailsCallback {
        fun onPostDetailsLoaded(postDetails: PostDetails)
        fun onDataNotAvailable()
    }

    fun getPostDetails(callback: LoadPostDetailsCallback)

}