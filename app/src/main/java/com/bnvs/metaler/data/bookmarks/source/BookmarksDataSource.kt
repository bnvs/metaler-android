package com.bnvs.metaler.data.bookmarks.source

import com.bnvs.metaler.data.bookmarks.model.*

interface BookmarksDataSource {

    fun addBookmark(
        request: AddBookmarkRequest,
        onSuccess: (response: AddBookmarkResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun deleteBookmark(
        bookmarkId: Int,
        onSuccess: (response: DeleteBookmarkResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun getMyBookmarks(
        request: BookmarksRequest,
        onSuccess: (response: BookmarksResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

}