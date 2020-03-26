package com.bnvs.metaler.data.postdetails.source

import com.bnvs.metaler.data.postdetails.PostDetails

interface PostDetailsDataSource {

    interface LoadPostDetailsCallback {
        fun onPostDetailsLoaded(postDetails: PostDetails)
        fun onDataNotAvailable()
    }

    fun getPostDetails(callback: LoadPostDetailsCallback)

}