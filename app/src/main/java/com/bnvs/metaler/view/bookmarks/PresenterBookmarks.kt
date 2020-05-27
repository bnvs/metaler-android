package com.bnvs.metaler.view.bookmarks

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.bnvs.metaler.data.bookmarks.model.BookmarksRequest
import com.bnvs.metaler.data.bookmarks.model.BookmarksResponse
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.view.detail.ActivityDetail

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
        openMaterialsList()
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

    override fun loadMoreBookmarkPosts(bookmarksRequest: BookmarksRequest) {
        bookmarksRepository.getMyBookmarks(
            bookmarksRequest,
            onSuccess = { response: BookmarksResponse ->
                if (response.is_next) {
                    view.showMoreBookmarkPostsList(response.posts)
                } else {
                    view.removeLoadingView()
                    Toast.makeText(
                        context,
                        "마지막 아이템입니다.",
                        Toast.LENGTH_LONG
                    ).show()
                }
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

    override fun getCategoryType(): String {
        return categoryType
    }

    override fun openMaterialsList() {
        pageNum = 0
        loadBookmarkPosts(requestPosts("materials"))
    }

    override fun openManufacturesList() {
        pageNum = 0
        loadBookmarkPosts(requestPosts("manufacture"))
    }

    override fun openPostDetail(postId: Int) {
        val detailIntent = Intent(context, ActivityDetail::class.java)
        detailIntent.putExtra("POST_ID", postId)
        context.startActivity(detailIntent)
    }

    override fun deleteBookmark(bookmarkId: Int) {
        bookmarksRepository.deleteBookmark(
            bookmarkId,
            onSuccess = {
                Toast.makeText(
                    context,
                    "북마크가 취소되었습니다.",
                    Toast.LENGTH_SHORT
                ).show()

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

}
