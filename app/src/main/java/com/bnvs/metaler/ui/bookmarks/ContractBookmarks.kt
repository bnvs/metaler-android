package com.bnvs.metaler.ui.bookmarks

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.bookmarks.model.Bookmark
import com.bnvs.metaler.data.bookmarks.model.BookmarksRequest
import com.bnvs.metaler.util.TapBarContract

interface ContractBookmarks {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showPostDetailUi(postId: Int)

        fun showMaterialsList(posts: List<Bookmark>)

        fun showManufacturesList(posts: List<Bookmark>)

        fun showBookmarkDeleteDialog(postId: Int)

    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadBookmarkPosts(bookmarksRequest: BookmarksRequest)

        fun requestPosts(categoryId: Int): BookmarksRequest

        fun openMaterialsList()

        fun openManufacturesList()

        fun openPostDetail(postId: Int)

        fun openBookmarkDelete(postId: Int)

        fun deleteBookmark(postId: Int)

    }
}