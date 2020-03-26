package com.bnvs.metaler_android.data.postdetails.source

import com.bnvs.metaler_android.data.postdetails.PostDetails

interface PostDetailsDataSource {

    interface LoadPostDetailsCallback {
        fun onPostDetailsLoaded(postDetails: PostDetails)
        fun onDataNotAvailable()
    }

    fun getPostDetails(callback: LoadPostDetailsCallback)

}