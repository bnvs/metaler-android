package com.bnvs.metaler.ui.bookmarks

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.bookmarks.model.Bookmark
import com.bnvs.metaler.data.bookmarks.model.BookmarksRequest
import com.bnvs.metaler.util.TapBarContract

interface ContractBookmarks {
    interface View : BaseView<Presenter>, TapBarContract.View {

        fun showBookmarkPostsList(bookmarks: List<Bookmark>)

        fun showMoreBookmarkPostsList(bookmarks: List<Bookmark>)

        fun removeLoadingView()

        fun showBookmarkDeleteDialog(bookmarkId: Int, position: Int)

    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadBookmarkPosts(bookmarksRequest: BookmarksRequest)

        fun loadMoreBookmarkPosts(bookmarksRequest: BookmarksRequest)

        fun requestPosts(categoryType: String): BookmarksRequest

        fun getCategoryType(): String

        fun openMaterialsList()

        fun openManufacturesList()

        fun openPostDetail(postId: Int)

        fun deleteBookmark(bookmarkId: Int)

    }
}