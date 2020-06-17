package com.bnvs.metaler.view.search

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkResponse
import com.bnvs.metaler.data.bookmarks.model.DeleteBookmarkRequest
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.data.posts.model.PostsResponse
import com.bnvs.metaler.data.posts.model.PostsWithContentRequest
import com.bnvs.metaler.data.posts.source.repository.PostsRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.view.detail.ActivityDetail
import org.koin.core.KoinComponent
import org.koin.core.inject

class PresenterSearch(
    private var categoryId: Int,
    private val context: Context,
    private val view: ContractSearch.View
) : ContractSearch.Presenter, KoinComponent {

    val TAG = "PresenterSearch.kt"

    private val postsRepository: PostsRepository by inject()
    private val bookmarksRepository: BookmarksRepository by inject()

    private lateinit var postsWithContentRequest: PostsWithContentRequest
    private lateinit var addBookmarkRequest: AddBookmarkRequest
    private lateinit var deleteBookmarkRequest: DeleteBookmarkRequest

    private var pageNum: Int = 0
    private val limit: Int = 10

    private val searchType: String = "content"

    init {
        view.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadSearchPosts(postsWithContentRequest: PostsWithContentRequest) {
        resetPageNum()
        postsRepository.getPostsWithSearchTypeContent(
            postsWithContentRequest,
            onSuccess = { response: PostsResponse ->
                if (response.posts.isNotEmpty()) {
                    view.hideError404()
                    view.showSearchPosts(response.posts)
                    view.showSearchTotalNum(response.post_count)
                } else {
                    view.showError404()
                }
            },
            onFailure = { e ->
                Toast.makeText(
                    context,
                    "서버 통신 실패 : ${NetworkUtil.getErrorMessage(e)}",
                    Toast.LENGTH_LONG
                ).show()
            },
            handleError = {

            }
        )
    }

    override fun loadMoreSearchPosts(postsWithContentRequest: PostsWithContentRequest) {
        postsRepository.getPostsWithSearchTypeContent(
            postsWithContentRequest,
            onSuccess = { response: PostsResponse ->
                if (response.posts.isNotEmpty()) {
                    view.showMoreSearchPosts(response.posts)
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
            },
            handleError = {

            }
        )
    }

    override fun requestSearchPosts(categoryId: Int, searchWord: String): PostsWithContentRequest {
        pageNum++
        this.categoryId = categoryId
        postsWithContentRequest = PostsWithContentRequest(
            categoryId,
            pageNum,
            limit,
            searchType,
            searchWord
        )
        return postsWithContentRequest
    }

    override fun resetPageNum() {
        pageNum = 0
    }

    override fun getCategoryId(): Int {
        return categoryId
    }

    override fun openPostDetail(postId: Int) {
        val detailIntent = Intent(context, ActivityDetail::class.java)
        detailIntent.putExtra("POST_ID", postId)
        context.startActivity(detailIntent)
    }

    override fun requestAddBookmark(postId: Int): AddBookmarkRequest {
        addBookmarkRequest = AddBookmarkRequest(postId)
        return addBookmarkRequest
    }

    override fun addBookmark(postId: Int, bookmarkId: Int, position: Int) {
        bookmarksRepository.addBookmark(
            requestAddBookmark(postId),
            onSuccess = { response: AddBookmarkResponse ->
                view.postAdapterAddBookmark(position, response.bookmark_id)
                Toast.makeText(
                    context,
                    "북마크에 추가되었습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onFailure = { e ->
                Toast.makeText(
                    context,
                    "서버 통신 실패 : ${NetworkUtil.getErrorMessage(e)}",
                    Toast.LENGTH_LONG
                ).show()
            },
            handleError = {}
        )
    }

    override fun deleteBookmark(bookmarkId: Int) {
        bookmarksRepository.deleteBookmark(
            DeleteBookmarkRequest(bookmarkId),
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
            },
            handleError = {}
        )
    }
}