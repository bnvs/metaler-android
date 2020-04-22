package com.bnvs.metaler.ui.manufactures

import android.content.Context
import android.widget.Toast
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsResponse
import com.bnvs.metaler.data.posts.source.repository.PostsRepository
import com.bnvs.metaler.network.NetworkUtil

class PresenterManufactures(
    private val context: Context,
    val view: ContractManufactures.View
) : ContractManufactures.Presenter {


    val TAG = "PresenterManufactures.kt"

    private val postRepository: PostsRepository = PostsRepository(context)

    private lateinit var postsRequest: PostsRequest

    private var pageNum: Int = 0

    lateinit var posts: List<Post>

    init {
        view.presenter = this
    }


    override fun start() {
        loadPosts(requestPosts())
    }


    override fun loadPosts(postsRequest: PostsRequest) {
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
            2,
            pageNum,
            10,
            null,
            null
        )
        return postsRequest
    }

    override fun refreshPosts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openPostDetail(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addBookmark(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteBookmark(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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