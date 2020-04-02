package com.bnvs.metaler.detail

import android.content.Context
import com.bnvs.metaler.data.bookmarks.source.BookmarksDataSource
import com.bnvs.metaler.data.bookmarks.source.BookmarksRepository
import com.bnvs.metaler.data.postdetails.PostDetails
import com.bnvs.metaler.data.postdetails.source.PostDetailsDataSource
import com.bnvs.metaler.data.postdetails.source.PostDetailsRepository

class PresenterDetail(
    private val context: Context,
    private val view: ContractDetail.View) : ContractDetail.Presenter {

    private val postDetailsRepository: PostDetailsRepository = PostDetailsRepository(context)
    private val bookmarksRepository: BookmarksRepository = BookmarksRepository(context)

    init {
        view.presenter = this
    }

    override fun start() {
//        loadPostDetail()
//        loadComments()
    }

    override fun loadPostDetail() {
        postDetailsRepository.getPostDetails(object : PostDetailsDataSource.LoadPostDetailsCallback{
            override fun onPostDetailsLoaded(postDetails: PostDetails) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataNotAvailable() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun loadComments() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addBookmark(postId: Int) {
        bookmarksRepository.addBookmark(object : BookmarksDataSource.AddBookmarkCallback{
            override fun onBookmarkAdded() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataNotAvailable() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
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

    override fun openMenu() {
        view.showMenuDialog()
    }

    override fun deletePost(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun modifyPost(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun ratePost(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}