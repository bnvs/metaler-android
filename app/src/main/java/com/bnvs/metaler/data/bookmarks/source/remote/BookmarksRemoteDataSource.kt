package com.bnvs.metaler.data.bookmarks.source.remote

import com.bnvs.metaler.data.bookmarks.source.BookmarksDataSource
import com.bnvs.metaler.network.RetrofitClient

object BookmarksRemoteDataSource : BookmarksDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun deleteBookmark(callback: BookmarksDataSource.DeleteBookmarkCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}