package com.bnvs.metaler.manufactures

import android.content.Context
import com.bnvs.metaler.data.posts.PostsResponse
import com.bnvs.metaler.data.posts.source.PostsDataSource
import com.bnvs.metaler.data.posts.source.PostsRepository
import com.bnvs.metaler.data.token.AccessToken
import com.bnvs.metaler.data.token.source.TokenDataSource
import com.bnvs.metaler.data.token.source.TokenRepository

class PresenterManufactures(
    private val context: Context,
    val view: ContractManufactures.View
) : ContractManufactures.Presenter {

    private val tokenRepository: TokenRepository = TokenRepository(context)

    private val postRepository: PostsRepository = PostsRepository(context)

    private var accessToken: String = ""

    init {
        view.presenter = this
    }


    override fun start() {
        getAccessToken()
        loadPosts()
    }

    override fun getAccessToken() {
        tokenRepository.getAccessToken(object : TokenDataSource.LoadAccessTokenCallback {
            override fun onTokenloaded(token: AccessToken) {
                accessToken = token.access_token //유효성 (24시간 지났는지) 검사
            }

            override fun onTokenNotExist() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun loadPosts() {
        postRepository.getPosts(
            accessToken,
            postsRequest,
            object : PostsDataSource.LoadPostsCallback {
                override fun onPostsLoaded(postsResponse: PostsResponse) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponseError(message: String) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onFailure(t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
    }


    override fun requestPosts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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