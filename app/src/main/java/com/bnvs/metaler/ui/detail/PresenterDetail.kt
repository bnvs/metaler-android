package com.bnvs.metaler.ui.detail

import android.content.Context
import android.view.View
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.data.comments.model.AddEditCommentRequest
import com.bnvs.metaler.data.comments.model.Comment
import com.bnvs.metaler.data.comments.model.CommentsRequest
import com.bnvs.metaler.data.comments.source.repository.CommentsRepository
import com.bnvs.metaler.data.postdetails.model.PostDetails
import com.bnvs.metaler.data.postdetails.model.RatingRequest
import com.bnvs.metaler.data.postdetails.source.repository.PostDetailsRepository
import com.bnvs.metaler.data.profile.source.repository.ProfileRepository
import com.bnvs.metaler.data.user.certification.model.User
import com.bnvs.metaler.network.NetworkUtil
import java.text.SimpleDateFormat
import java.util.*

class PresenterDetail(
    private val postId: Int,
    private val view: ContractDetail.View,
    context: Context
) : ContractDetail.Presenter {

    private val postDetailsRepository = PostDetailsRepository()
    private val commentsRepository = CommentsRepository()
    private val bookmarksRepository = BookmarksRepository()
    private val profileRepository = ProfileRepository(context)

    private lateinit var postDetails: PostDetails
    private lateinit var userInfo: User
    private var userId = 0

    private var page = 0
    private val limit = 10
    private var commentCount = 0
    private var isNext = false

    override fun start() {
        getUserInfo()
        loadPostDetail()
    }

    private fun getUserInfo() {
        profileRepository.getUserInfo(
            onUserInfoLoaded = { user ->
                this.userInfo = user
                this.userId = user.id
            },
            onUserInfoNotExist = {
                view.showErrorToast("유저 정보를 불러오는 데 실패했습니다")
            }
        )
    }

    override fun loadPostDetail() {
        postDetailsRepository.getPostDetails(
            postId,
            onSuccess = { response ->
                postDetails = response
                view.run {
                    setBookmarkButton(response.is_bookmark)
                    initPostDetailAdapter(response)
                    loadComments()
                    initPostDetailScrollListener()
                }
            },
            onFailure = { e ->
                view.apply {
                    showErrorToast(
                        "게시물을 불러오는데 실패했습니다" +
                                "\n ${NetworkUtil.getErrorMessage(e)}"
                    )
                    finishActivity()
                }
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
            onFailure = { e ->
                view.apply {
                    showErrorToast(
                        "댓글을 불러오는데 실패했습니다" +
                                "\n ${NetworkUtil.getErrorMessage(e)}"
                    )
                }
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
                onFailure = { e ->
                    view.apply {
                        showErrorToast(
                            "댓글을 불러오는데 실패했습니다" +
                                    "\n ${NetworkUtil.getErrorMessage(e)}"
                        )
                    }
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

    override fun openAddBookmark() {
        view.showBookmarkAddDialog()
    }

    override fun openDeleteBookmark() {
        view.showBookmarkDeleteDialog()
    }

    override fun addBookmark() {
        bookmarksRepository.addBookmark(
            AddBookmarkRequest(postId),
            onSuccess = {
                postDetails.is_bookmark = true
                view.apply {
                    setBookmarkButton(true)
                    showBookmarkAddedToast()
                }
            },
            onFailure = { e ->
                view.apply {
                    showErrorToast(
                        "북마크 추가에 실패했습니다" +
                                "\n ${NetworkUtil.getErrorMessage(e)}"
                    )
                }
            }
        )
    }

    override fun deleteBookmark() {
        bookmarksRepository.deleteBookmark(
            postId,
            onSuccess = {
                postDetails.is_bookmark = false
                view.apply {
                    setBookmarkButton(false)
                    showBookmarkDeletedToast()
                }
            },
            onFailure = { e ->
                view.apply {
                    showErrorToast(
                        "북마크 취소에 실패했습니다" +
                                "\n ${NetworkUtil.getErrorMessage(e)}"
                    )
                }
            }
        )
    }


    override fun openMenu(v: View) {
        view.showPopupMenu(v)
    }

    override fun openDeletePost() {
        if (userId == postDetails.user_id) {
            view.showDeletePostDialog()
        } else {
            view.showDeleteFailedDialog()
        }
    }

    override fun deletePost() {
        postDetailsRepository.deletePost(
            postId,
            onSuccess = {
                view.showPostDeletedToast()
                view.finishActivity()
            },
            onFailure = { e ->
                view.apply {
                    showErrorToast(
                        "게시글 삭제에 실패했습니다" +
                                "\n ${NetworkUtil.getErrorMessage(e)}"
                    )
                }
            }
        )
    }

    override fun modifyPost() {
        if (userId == postDetails.user_id) {
            view.openEditPostUi(postId)
        } else {
            view.showEditPostFailedDialog()
        }
    }

    override fun likePost() {
        when (postDetails.rating) {
            -1 -> view.showAlreadyRatedDialog()
            0 -> ratePost(1)
            1 -> unRatePost()
        }
    }

    override fun dislikePost() {
        when (postDetails.rating) {
            -1 -> unRatePost()
            0 -> ratePost(-1)
            1 -> view.showAlreadyRatedDialog()
        }
    }

    override fun ratePost(rating: Int) {
        postDetailsRepository.ratePost(
            postId,
            RatingRequest(rating),
            onSuccess = {
                postDetails.rating = rating
                when (rating) {
                    -1 -> {
                        view.dislikePost()
                        postDetails.disliked += 1
                    }
                    1 -> {
                        view.likePost()
                        postDetails.liked += 1
                    }
                }
            },
            onFailure = { e ->
                view.apply {
                    showErrorToast(
                        "게시불 평가에 실패했습니다" +
                                "\n ${NetworkUtil.getErrorMessage(e)}"
                    )
                }
            }
        )
    }

    override fun unRatePost() {
        postDetailsRepository.unRatePost(
            postId,
            onSuccess = {
                when (postDetails.rating) {
                    -1 -> {
                        view.cancelDislikePost()
                        postDetails.disliked -= 1
                    }
                    1 -> {
                        view.cancelLikePost()
                        postDetails.liked -= 1
                    }
                }
                postDetails.rating = 0
            },
            onFailure = { e ->
                view.apply {
                    showErrorToast(
                        "게시물 평가 취소에 실패했습니다" +
                                "\n ${NetworkUtil.getErrorMessage(e)}"
                    )
                }
            }
        )
    }

    override fun addComment(comment: String) {
        val dataFormat = SimpleDateFormat("yyyy.MM.dd aa hh:mm")
        val date = dataFormat.format(Date(System.currentTimeMillis()))

        commentsRepository.addComment(
            postId,
            AddEditCommentRequest(comment),
            onSuccess = { response ->
                view.run {
                    addComment(
                        Comment(
                            response.comment_id,
                            userId,
                            comment,
                            date,
                            userInfo.profile_nickname,
                            userInfo.profile_image_url
                        )
                    )
                    clearCommentInput()
                    hideSoftInput()
                    scrollToEnd()
                }
            },
            onFailure = { e ->
                view.apply {
                    showErrorToast(
                        "댓글 작성에 실패했습니다" +
                                "\n ${NetworkUtil.getErrorMessage(e)}"
                    )
                }
            }
        )
    }
}