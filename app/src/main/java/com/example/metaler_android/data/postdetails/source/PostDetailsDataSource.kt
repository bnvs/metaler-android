package com.example.metaler_android.data.postdetails.source

import com.example.metaler_android.data.postdetails.PostDetails

interface PostDetailsDataSource {

    interface LoadPostDetailsCallback {
        fun onPostDetailsLoaded(postDetails: PostDetails)
        fun onDataNotAvailable()
    }

    fun getPostDetails(callback: LoadPostDetailsCallback)

}