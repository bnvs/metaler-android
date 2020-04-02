package com.bnvs.metaler.data.posts.source

interface PostDataSource {

    interface LoadPostsCallback {
        fun onPostsLoaded(posts: Posts)
        fun onDataNotAvailable()
    }

    fun getPosts(callback: LoadPostsCallback)

}