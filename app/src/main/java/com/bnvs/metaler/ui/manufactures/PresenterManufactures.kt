package com.bnvs.metaler.ui.manufactures

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkResponse
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsResponse
import com.bnvs.metaler.data.posts.model.PostsWithTagRequest
import com.bnvs.metaler.data.posts.source.repository.PostsRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.ui.detail.ActivityDetail

class PresenterManufactures(
    private val context: Context,
    val view: ContractManufactures.View
) : ContractManufactures.Presenter {


    val TAG = "PresenterManufactures.kt"

    private val postRepository: PostsRepository = PostsRepository(context)
    private val bookmarksRepository: BookmarksRepository = BookmarksRepository()

    private lateinit var postsRequest: PostsRequest
    private lateinit var postsWithTagRequest: PostsWithTagRequest
    private lateinit var addBookmarkRequest: AddBookmarkRequest

    private var pageNum: Int = 0
    private var categoryId: Int = 10 //가공탭은 categoryId 10으로 고정

    private var searchType: String? = null
    lateinit var searchWord: List<String>

    lateinit var posts: List<Post>

    init {
        view.presenter = this
    }


    override fun start() {
        resetPageNum()
        loadPosts(requestPosts())
    }


    override fun loadPosts(postsRequest: PostsRequest) {
        postRepository.getPosts(
            postsRequest,
            onSuccess = { response: PostsResponse ->
                if (response.post_count > 0) {
                    view.hideError404()
                    view.showPosts(response.posts)
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
            }
        )
    }

    override fun loadMorePosts(postsRequest: PostsRequest) {
        postRepository.getPosts(
            postsRequest,
            onSuccess = { response: PostsResponse ->
                if (response.posts.isNotEmpty()) {
                    view.showMorePosts(response.posts)
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

    override fun loadSearchTagPosts(postsWithTagRequest: PostsWithTagRequest) {
        resetPageNum()
        postRepository.getPostsWithSearchTypeTag(
            postsWithTagRequest,
            onSuccess = { response: PostsResponse ->
                if (response.posts.isNotEmpty()) {
                    view.hideError404()
                    view.showRefreshPosts(response.posts)
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
            }
        )
    }

    override fun loadMoreSearchTagPosts(postsWithTagRequest: PostsWithTagRequest) {
        postRepository.getPostsWithSearchTypeTag(
            postsWithTagRequest,
            onSuccess = { response: PostsResponse ->
                if (response.posts.isNotEmpty()) {
                    view.showMorePosts(response.posts)
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

    override fun refreshPosts(postsRequest: PostsRequest) {
        resetPageNum()
        postRepository.getPosts(
            postsRequest,
            onSuccess = { response: PostsResponse ->
                resetPageNum()
                view.showRefreshPosts(response.posts)
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

    override fun refreshTagSearchPosts(postsWithTagRequest: PostsWithTagRequest) {
        resetPageNum()
        postRepository.getPostsWithSearchTypeTag(
            postsWithTagRequest,
            onSuccess = { response: PostsResponse ->
                view.showRefreshPosts(response.posts)
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

    override fun resetPageNum() {
        pageNum = 0
    }

    override fun getCategoryId(): Int {
        return categoryId
    }

    // getPosts api 요청 request body 반환하는 함수
    override fun requestPosts(): PostsRequest {
        pageNum++

        postsRequest = PostsRequest(
            10,
            pageNum,
            10
        )
        return postsRequest
    }

    override fun requestAddSearchTag(
        categoryId: Int,
        searchType: String,
        searchWord: List<String>
    ): PostsWithTagRequest {
        pageNum++
        this.searchType = searchType
        this.searchWord = searchWord
        postsWithTagRequest = PostsWithTagRequest(
            categoryId,
            pageNum,
            10,
            searchType,
            searchWord
        )
        return postsWithTagRequest
    }

    // addBookmark api 요청을 반환하는 함수
    override fun requestAddBookmark(postId: Int): AddBookmarkRequest {
        addBookmarkRequest = AddBookmarkRequest(postId)
        return addBookmarkRequest
    }

    override fun openPostDetail(postId: Int) {
        val detailIntent = Intent(context, ActivityDetail::class.java)
        detailIntent.putExtra("POST_ID", postId)
        context.startActivity(detailIntent)
    }

    override fun addBookmark(postId: Int, bookmarkId: Int, position: Int) {
        bookmarksRepository.addBookmark(
            requestAddBookmark(postId),
            onSuccess = { response: AddBookmarkResponse ->
                Log.d("프레젠터 ", "response.bookmark_id ? : ${response.bookmark_id}")
                view.postAdapterAddBookmark(position, response.bookmark_id )
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
            }
        )
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