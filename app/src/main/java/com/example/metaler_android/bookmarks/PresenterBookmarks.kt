package com.example.metaler_android.bookmarks

import android.content.Context
import com.example.metaler_android.data.bookmarks.source.BookmarksDataSource
import com.example.metaler_android.data.bookmarks.source.BookmarksRepository

class PresenterBookmarks (
    private val context: Context,
    private val view: ContractBookmarks.View) : ContractBookmarks.Presenter {

    private val bookmarksRepository: BookmarksRepository = BookmarksRepository(context)

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteBookmark(postId: Int) {
        bookmarksRepository.deleteBookmark(object : BookmarksDataSource.DeleteBookmarkCallback{
            override fun onBookmarkDeleted() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataNotAvailable() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

}
