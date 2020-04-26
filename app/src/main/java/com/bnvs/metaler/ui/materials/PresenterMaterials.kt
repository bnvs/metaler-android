package com.bnvs.metaler.ui.materials

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkResponse
import com.bnvs.metaler.data.bookmarks.model.DeleteBookmarkRequest
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.data.categories.model.Categories
import com.bnvs.metaler.data.categories.source.repository.CategoriesRepository
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsResponse
import com.bnvs.metaler.data.posts.source.repository.PostsRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.ui.detail.ActivityDetail

class PresenterMaterials(
    private val context: Context,
    private val view: ContractMaterials.View
) : ContractMaterials.Presenter {

    val TAG = "PresenterMaterials.kt"

    private val postRepository: PostsRepository = PostsRepository(context)
    private val bookmarksRepository: BookmarksRepository = BookmarksRepository(context)
    private val categoriesRepository: CategoriesRepository = CategoriesRepository(context)

    private lateinit var postsRequest: PostsRequest
    private lateinit var addBookmarkRequest: AddBookmarkRequest
    private lateinit var deleteBookmarkRequest: DeleteBookmarkRequest

    private var pageNum: Int = 0

    lateinit var posts: List<Post>

    init {
        view.presenter = this
    }

    override fun start() {
        loadPosts(requestPosts())
    }

    override fun loadCategories() {
        categoriesRepository.getCategories(
            onSuccess = { response: Categories -> view.showCategories(response.categories)},
            onFailure = { e ->
                Toast.makeText(
                    context,
                    "서버 통신 실패 : ${NetworkUtil.getErrorMessage(e)}",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }

    override fun loadPosts(postsRequest: PostsRequest) {
        resetPageNum()
        postRepository.getPosts(
            postsRequest,
            onSuccess = { response: PostsResponse ->
                view.showPosts(response.posts)
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

    override fun loadMorePosts(postsRequest: PostsRequest) {
        postRepository.getPosts(
            postsRequest,
            onSuccess = { response: PostsResponse ->
                if (response.is_next) {
                    view.showMorePosts(response.posts)
                } else {
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


    // getPosts api 요청 request body 반환하는 함수
    override fun requestPosts(): PostsRequest {
        pageNum++
        postsRequest = PostsRequest(
            1,
            pageNum,
            10,
            null,
            null
        )
        return postsRequest
    }

    // addBookmark api 요청을 반환하는 함수
    override fun requestAddBookmark(postId: Int): AddBookmarkRequest {
        addBookmarkRequest = AddBookmarkRequest(postId)
        return addBookmarkRequest
    }

    override fun requestDeleteBookmark(postId: Int): DeleteBookmarkRequest {
        deleteBookmarkRequest = DeleteBookmarkRequest(postId)
        return deleteBookmarkRequest
    }

    override fun resetPageNum() {
        pageNum = 0
    }

    override fun openPostDetail(postId: Int) {
        val detailIntent = Intent(context, ActivityDetail::class.java)
        context.startActivity(detailIntent)
    }

    override fun addBookmark(postId: Int) {
        bookmarksRepository.addBookmark(
            requestAddBookmark(postId),
            onSuccess = { response: AddBookmarkResponse ->
                Toast.makeText(
                    context,
                    "${response.bookmark_id} 북마크에 추가되었습니다.",
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

    override fun deleteBookmark(postId: Int) {
        bookmarksRepository.deleteBookmark(
            postId,
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

    override fun openSearch() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addSearchTag() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearSearchTagBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteSearchTag() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}