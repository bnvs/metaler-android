package com.bnvs.metaler.data.bookmarks.source.local

import android.content.Context
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkResponse
import com.bnvs.metaler.data.bookmarks.model.BookmarksRequest
import com.bnvs.metaler.data.bookmarks.model.BookmarksResponse
import com.bnvs.metaler.util.constants.LOCAL_BOOKMARKS_DATA

class BookmarksLocalDataSourceImpl(context: Context) : BookmarksLocalDataSource {

    private val shared =
        context.getSharedPreferences(LOCAL_BOOKMARKS_DATA, Context.MODE_PRIVATE)

    override fun addBookmark(
        request: AddBookmarkRequest,
        onSuccess: (response: AddBookmarkResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteBookmark(
        bookmarkId: Int,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getMyBookmarks(
        request: BookmarksRequest,
        onSuccess: (response: BookmarksResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}