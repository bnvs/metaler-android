package com.bnvs.metaler.ui.materials

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkResponse
import com.bnvs.metaler.data.bookmarks.model.DeleteBookmarkRequest
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.data.categories.source.repository.CategoriesRepository
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsResponse
import com.bnvs.metaler.data.posts.model.PostsWithTagRequest
import com.bnvs.metaler.data.posts.source.repository.PostsRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.ui.detail.ActivityDetail

class PresenterMaterials(
    private val context: Context,
    private val view: ContractMaterials.View
) : ContractMaterials.Presenter {

    val TAG = "PresenterMaterials.kt"

    private val postRepository: PostsRepository = PostsRepository(context)
    private val bookmarksRepository: BookmarksRepository = BookmarksRepository()
    private val categoriesRepository: CategoriesRepository = CategoriesRepository()

    private lateinit var postsRequest: PostsRequest
    private lateinit var postsWithTagRequest: PostsWithTagRequest
    private lateinit var addBookmarkRequest: AddBookmarkRequest
    private lateinit var deleteBookmarkRequest: DeleteBookmarkRequest

    private var pageNum: Int = 0
    private var categoryId: Int = 0

    private var searchType: String? = null
    lateinit var searchWord: List<String>

    lateinit var posts: List<Post>

    init {
        view.presenter = this
    }

    override fun start() {
        resetPageNum()
        loadPosts(requestPosts(1))
        loadCategories()
    }

    override fun loadCategories() {
        categoriesRepository.getCategories(
            onSuccess = { response -> view.showCategories(response) },
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
                    //페이지 1로 초기화
                    resetPageNum()
                    //데이터 없을 때 띄우는 아이콘을 없앰
                    view.hideError404()
                    //기존에 있던 뷰의 데이터를 지우고 새로 받아온 태그 검색 결과 데이터를 추가하는 부분
                    view.showRefreshPosts(response.posts)
                } else {
                    //데이터 없을 때 아이콘을 띄움
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

    //태그랑 카테고리 한꺼번에 검색
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

    //스와이프 새로고침 했을 경우
    override fun refreshPosts(postsRequest: PostsRequest) {
        resetPageNum()
        postRepository.getPosts(
            postsRequest,
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



    // getPosts api 요청 request body 반환하는 함수
    override fun requestPosts(categoryId: Int): PostsRequest {
        pageNum++
        this.categoryId = categoryId
        postsRequest = PostsRequest(
            categoryId,
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
        this.categoryId = categoryId
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

    override fun requestDeleteBookmark(postId: Int): DeleteBookmarkRequest {
        deleteBookmarkRequest = DeleteBookmarkRequest(postId)
        return deleteBookmarkRequest
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

    override fun addBookmark(postId: Int, bookmarkId: Int, position: Int) {
        bookmarksRepository.addBookmark(
            requestAddBookmark(postId),
            onSuccess = { response: AddBookmarkResponse ->
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

}