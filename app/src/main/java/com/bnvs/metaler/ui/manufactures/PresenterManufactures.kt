package com.bnvs.metaler.ui.manufactures

import android.content.Context
import android.util.Log
import com.bnvs.metaler.data.posts.Post
import com.bnvs.metaler.data.posts.PostsRequest
import com.bnvs.metaler.data.posts.PostsResponse
import com.bnvs.metaler.data.posts.source.PostsDataSource
import com.bnvs.metaler.data.posts.source.PostsRepository
import com.bnvs.metaler.data.token.source.TokenRepository

class PresenterManufactures(
    private val context: Context,
    val view: ContractManufactures.View
) : ContractManufactures.Presenter {

    val TAG = "PresenterManufactures.kt"

    private val tokenRepository: TokenRepository = TokenRepository(context)

    private val postRepository: PostsRepository = PostsRepository(context)

    private var accessToken: String = ""

    private lateinit var postsRequest: PostsRequest

    private var pageNum: Int = 0

    lateinit var posts: List<Post>

    init {
        view.presenter = this
    }


    override fun start() {
        getAccessToken()
        loadPosts(requestPosts())
    }

    override fun getAccessToken() {
        /*tokenRepository.getAccessToken(object : TokenDataSource.LoadAccessTokenCallback {
            override fun onTokenloaded(token: AccessToken) {
                accessToken = token.access_token //유효성 (24시간 지났는지) 검사
            }

            override fun onTokenNotExist() {

            }
        })*/
    }

    override fun loadPosts(postsRequest: PostsRequest) {
        postRepository.getPosts(
            accessToken,
            postsRequest,
            object : PostsDataSource.LoadPostsCallback {
                override fun onPostsLoaded(postsResponse: PostsResponse) {
                    view.showPosts(postsResponse.posts)
                }

                override fun onResponseError(message: String) {
                    Log.d(TAG, "message ? : $message")
                }

                override fun onFailure(t: Throwable) {
                    Log.d(TAG, "t ? : $t")
                }
            })
    }


    // getPosts api 요청 request body 반환하는 함수
    override fun requestPosts(): PostsRequest {
        pageNum ++
        postsRequest = PostsRequest(
            1,
            pageNum,
            20,
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