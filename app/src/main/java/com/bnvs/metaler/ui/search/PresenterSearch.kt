package com.bnvs.metaler.ui.search

import android.content.Context
import com.bnvs.metaler.data.posts.model.PostsWithContentRequest
import com.bnvs.metaler.data.posts.source.repository.PostsRepository

class PresenterSearch(
    private val context: Context,
    private val view: ContractSearch.View
) : ContractSearch.Presenter {

    val TAG = "PresenterSearch.kt"

    private val postsRepository: PostsRepository = PostsRepository(context)

    init {
        view.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadSearchPosts(postsWithContentRequest: PostsWithContentRequest) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requestSearchPosts(categoryType: Int): PostsWithContentRequest {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}