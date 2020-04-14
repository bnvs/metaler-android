package com.bnvs.metaler.data.bookmarks.source

import android.content.Context
import com.bnvs.metaler.data.bookmarks.*
import com.bnvs.metaler.data.bookmarks.source.remote.BookmarksRemoteDataSource

class BookmarksRepository(context: Context) : BookmarksDataSource {

    private val bookmarksRemoteDataSource = BookmarksRemoteDataSource

    override fun addBookmark(
        request: AddBookmarkRequest,
        onSuccess: (response: AddBookmarkResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        bookmarksRemoteDataSource.addBookmark(request, onSuccess, onFailure)
    }

    override fun deleteBookmark(
        bookmarkId: Int,
        onSuccess: (response: DeleteBookmarkResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        bookmarksRemoteDataSource.deleteBookmark(bookmarkId, onSuccess, onFailure)
    }

    override fun getMyBookmarks(
        request: BookmarksRequest,
        onSuccess: (response: BookmarksResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        bookmarksRemoteDataSource.getMyBookmarks(request, onSuccess, onFailure)
    }
}