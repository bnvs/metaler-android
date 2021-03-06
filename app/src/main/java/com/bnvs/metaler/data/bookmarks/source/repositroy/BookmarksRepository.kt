package com.bnvs.metaler.data.bookmarks.source.repositroy

import com.bnvs.metaler.data.bookmarks.model.*

interface BookmarksRepository {
    fun addBookmark(
        request: AddBookmarkRequest,
        onSuccess: (response: AddBookmarkResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )

    fun deleteBookmark(
        request: DeleteBookmarkRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )

    fun getMyBookmarks(
        request: BookmarksRequest,
        onSuccess: (response: BookmarksResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )
}