package com.example.metaler_android.data.bookmarks.source

import android.content.Context
import com.example.metaler_android.data.bookmarks.source.remote.BookmarksRemoteDataSource

class BookmarksRepository(context: Context) : BookmarksDataSource {

    private val bookmarksRemoteDataSource = BookmarksRemoteDataSource

    override fun deleteBookmark(callback: BookmarksDataSource.DeleteBookmarkCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}