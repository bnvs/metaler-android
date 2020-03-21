package com.example.metaler_android.manufactures

import android.content.Context
import com.example.metaler_android.data.post.source.PostRepository

class PresenterManufactures(context: Context, val view: ContractManufactures.View) : ContractManufactures.Presenter {

    private val postRepository: PostRepository = PostRepository(context)

    init {
        view.presenter = this
    }

    override fun start() {
        loadPosts()
    }

    override fun loadPosts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openPostDetail(clickedPostId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addBookmark() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteBookmark() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openSearch() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun AddsearchTag() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearSearchTagBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteSearchTag() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setTapBar(context: Context) {
        view.setTapBarListener(context)
    }
}