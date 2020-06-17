package com.bnvs.metaler.data.bookmarks.source.local

import com.bnvs.metaler.data.bookmarks.model.*

interface BookmarksLocalDataSource {
    fun addBookmark(
        request: AddBookmarkRequest,
        onSuccess: (response: AddBookmarkResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun deleteBookmark(
        request: DeleteBookmarkRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun getMyBookmarks(
        request: BookmarksRequest,
        onSuccess: (response: BookmarksResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}