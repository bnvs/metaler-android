package com.example.metaler_android.data.bookmarks.source.remote

import com.example.metaler_android.data.bookmarks.source.BookmarksDataSource
import com.example.metaler_android.network.RetrofitClient

object BookmarksRemoteDataSource : BookmarksDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun deleteBookmark(callback: BookmarksDataSource.DeleteBookmarkCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}