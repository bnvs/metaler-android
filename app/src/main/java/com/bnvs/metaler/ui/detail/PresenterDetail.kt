package com.bnvs.metaler.ui.detail

import android.view.View
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.data.comments.model.CommentsRequest
import com.bnvs.metaler.data.comments.source.repository.CommentsRepository
import com.bnvs.metaler.data.postdetails.model.PostDetails
import com.bnvs.metaler.data.postdetails.source.repository.PostDetailsRepository

class PresenterDetail(
    private val postId: Int,
    private val view: ContractDetail.View
) : ContractDetail.Presenter {

    private val postDetailsRepository = PostDetailsRepository()
    private val commentsRepository = CommentsRepository()
    private val bookmarksRepository = BookmarksRepository()

    private lateinit var postDetails: PostDetails

    private var page = 0
    private val limit = 10
    private var commentCount = 0
    private var isNext = false

    override fun start() {
        loadPostDetail()
    }

    override fun loadPostDetail() {
        postDetailsRepository.getPostDetails(
            postId,
            onSuccess = { response ->
                postDetails = response
                view.run {
                    initPostDetailAdapter(response)
                    loadComments()
                    initPostDetailScrollListener()
                }
            },
            onFailure = {

            }
        )
    }

    override fun loadComments() {
        resetPage()
        commentsRepository.getComments(
            postId,
            getCommentsRequest(),
            onSuccess = { response ->
                view.showComments(response.comments)
                commentCount = response.comment_count
                isNext = response.is_next
            },
            onFailure = {

            }
        )
    }

    override fun loadMoreComments() {
        view.setCommentsLoading(true)
        val handler = android.os.Handler()
        handler.postDelayed({
            commentsRepository.getComments(
                postId,
                getCommentsRequest(),
                onSuccess = { response ->
                    view.setCommentsLoading(false)
                    view.addComments(response.comments)
                    commentCount = response.comment_count
                    isNext = response.is_next
                },
                onFailure = {

                }
            )
        }, 1000)
    }

    override fun hasNextPage(): Boolean {
        return isNext
    }

    override fun setHasNextPage(b: Boolean) {
        isNext = b
    }

    private fun resetPage() {
        page = 0
    }

    private fun getCommentsRequest(): CommentsRequest {
        page++
        return CommentsRequest(page, limit)
    }

    override fun addBookmark() {
        bookmarksRepository.addBookmark(
            AddBookmarkRequest(postId),
            onSuccess = {

            },
            onFailure = {

            }
        )
    }

    override fun deleteBookmark() {
        bookmarksRepository.deleteBookmark(
            postId,
            onSuccess = {

            },
            onFailure = {

            }
        )
    }


    override fun openMenu(v: View) {
        view.showOptionsMenu(v)
    }

    override fun deletePost() {
        postDetailsRepository.deletePost(
            postId,
            onSuccess = {

            },
            onFailure = {

            }
        )
    }

    override fun modifyPost() {
        view.openEditPostUi(postId)
    }

    override fun likePost() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dislikePost() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}