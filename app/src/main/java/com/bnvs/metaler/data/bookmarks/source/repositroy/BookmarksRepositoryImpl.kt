package com.bnvs.metaler.data.bookmarks.source.repositroy

import com.bnvs.metaler.data.bookmarks.model.*
import com.bnvs.metaler.data.bookmarks.source.local.BookmarksLocalDataSource
import com.bnvs.metaler.data.bookmarks.source.remote.BookmarksRemoteDataSource

class BookmarksRepositoryImpl(
    private val bookmarksLocalDataSource: BookmarksLocalDataSource,
    private val bookmarksRemoteDataSource: BookmarksRemoteDataSource
) : BookmarksRepository {

    override fun addBookmark(
        request: AddBookmarkRequest,
        onSuccess: (response: AddBookmarkResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        bookmarksRemoteDataSource.addBookmark(request, onSuccess, onFailure, handleError)
    }

    override fun deleteBookmark(
        request: DeleteBookmarkRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        bookmarksRemoteDataSource.deleteBookmark(request, onSuccess, onFailure, handleError)
    }

    override fun getMyBookmarks(
        request: BookmarksRequest,
        onSuccess: (response: BookmarksResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        bookmarksRemoteDataSource.getMyBookmarks(request, onSuccess, onFailure, handleError)
    }
}