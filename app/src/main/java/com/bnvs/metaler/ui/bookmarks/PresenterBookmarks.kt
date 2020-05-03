package com.bnvs.metaler.ui.bookmarks

import android.content.Context
import android.widget.Toast
import com.bnvs.metaler.data.bookmarks.model.BookmarksRequest
import com.bnvs.metaler.data.bookmarks.model.BookmarksResponse
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.network.NetworkUtil

class PresenterBookmarks(
    private val context: Context,
    private val view: ContractBookmarks.View
) : ContractBookmarks.Presenter {

    private val bookmarksRepository: BookmarksRepository =
        BookmarksRepository()

    private lateinit var bookmarksRequest: BookmarksRequest

    private var pageNum: Int = 0
    private var categoryType: String = ""

    init {
        view.presenter = this
    }

    override fun start() {
        loadBookmarkPosts(requestPosts("manufacture"))
    }

    override fun loadBookmarkPosts(bookmarksRequest: BookmarksRequest) {
        bookmarksRepository.getMyBookmarks(
            bookmarksRequest,
            onSuccess = { response: BookmarksResponse ->
                view.showBookmarkPostsList(response.posts)
            },
            onFailure = { e ->
                Toast.makeText(
                    context,
                    "서버 통신 실패 : ${NetworkUtil.getErrorMessage(e)}",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }

    override fun requestPosts(categoryType: String): BookmarksRequest {
        pageNum++
        this.categoryType = categoryType
        bookmarksRequest = BookmarksRequest(
            categoryType,
            pageNum,
            10
        )
        return bookmarksRequest
    }

    override fun openMaterialsList() {
        pageNum = 0
        loadBookmarkPosts(requestPosts("manufacture"))
    }

    override fun openManufacturesList() {
        pageNum = 0
        loadBookmarkPosts(requestPosts("materials"))
    }

    override fun openPostDetail(postId: Int) {
        view.showPostDetailUi(postId)
    }

    override fun openBookmarkDelete(postId: Int) {
        view.showBookmarkDeleteDialog(postId)
    }

    override fun deleteBookmark(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
