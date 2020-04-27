package com.bnvs.metaler.ui.bookmarks

import android.content.Context
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.data.posts.model.PostsRequest

class PresenterBookmarks(
    private val context: Context,
    private val view: ContractBookmarks.View
) : ContractBookmarks.Presenter {

    private val bookmarksRepository: BookmarksRepository =
        BookmarksRepository(context)

    private lateinit var postsRequest: PostsRequest

    private var pageNum: Int = 0
    private var categoryId: Int = 0

    init {
        view.presenter = this
    }

    override fun start() {
        loadMaterialsPost()
    }

    override fun loadMaterialsPost() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadManufacturePost() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requestPosts(categoryId: Int): PostsRequest {
        pageNum++
        this.categoryId = categoryId
        postsRequest = PostsRequest(
            categoryId,
            pageNum,
            10,
            null,
            null
        )
        return postsRequest
    }

    override fun openMaterialsList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openManufacturesList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openPostDetail(postId: Int) {
        view.showPostDetailUi(postId)
    }

    override fun openBookmarkDelete(postId: Int) {
        view.showBookmarkDeleteDialog(postId)
    }

    override fun deleteBookmark(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
