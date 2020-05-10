package com.bnvs.metaler.ui.search

import android.content.Context
import android.content.Intent
import com.bnvs.metaler.data.posts.model.PostsWithContentRequest
import com.bnvs.metaler.data.posts.source.repository.PostsRepository
import com.bnvs.metaler.ui.detail.ActivityDetail

class PresenterSearch(
    private var categoryId: Int,
    private val context: Context,
    private val view: ContractSearch.View
) : ContractSearch.Presenter {

    val TAG = "PresenterSearch.kt"

    private val postsRepository: PostsRepository = PostsRepository(context)

    private lateinit var postsWithContentRequest: PostsWithContentRequest

    private var pageNum: Int = 0

    private val searchType: String = "content"

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
}