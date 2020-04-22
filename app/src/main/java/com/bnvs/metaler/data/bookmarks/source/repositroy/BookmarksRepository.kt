package com.bnvs.metaler.data.bookmarks.source.repositroy

import android.content.Context
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkResponse
import com.bnvs.metaler.data.bookmarks.model.BookmarksRequest
import com.bnvs.metaler.data.bookmarks.model.BookmarksResponse
import com.bnvs.metaler.data.bookmarks.source.BookmarksDataSource
import com.bnvs.metaler.data.bookmarks.source.remote.BookmarksRemoteDataSource

class BookmarksRepository(context: Context) :
    BookmarksDataSource {

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
        onSuccess: () -> Unit,
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