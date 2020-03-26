package com.bnvs.metaler.detail

import android.content.Context

class PresenterDetail(
    private val context: Context,
    private val view: ContractDetail.View) : ContractDetail.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadPostDetail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadComments() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addBookmark(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteBookmark(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openMenu() {
        view.showMenuDialog()
    }

    override fun deletePost(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun modifyPost(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}