package com.bnvs.metaler.data.bookmarks.source.remote

import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkResponse
import com.bnvs.metaler.data.bookmarks.model.BookmarksRequest
import com.bnvs.metaler.data.bookmarks.model.BookmarksResponse

interface BookmarksRemoteDataSource {

    fun addBookmark(
        request: AddBookmarkRequest,
        onSuccess: (response: AddBookmarkResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun deleteBookmark(
        bookmarkId: Int,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun getMyBookmarks(
        request: BookmarksRequest,
        onSuccess: (response: BookmarksResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}