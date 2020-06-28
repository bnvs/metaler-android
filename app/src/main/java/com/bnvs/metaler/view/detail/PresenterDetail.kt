package com.bnvs.metaler.view.detail

import android.view.View
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.model.DeleteBookmarkRequest
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
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*

class PresenterDetail(
    private val postId: Int,
    private val view: ContractDetail.View
) : ContractDetail.Presenter, KoinComponent {

    private val postDetailsRepository: PostDetailsRepository by inject()
    private val commentsRepository: CommentsRepository by inject()
    private val bookmarksRepository: BookmarksRepository by inject()
    private val profileRepository: ProfileRepository by inject()

    private lateinit var postDetails: PostDetails
    private lateinit var userInfo: User
    private var userId = 0
    private lateinit var tempComment: Comment
    private var tempCommentIndex = -1

    private var page = 0
    private val limit = 10
    private var commentCount = 0
    private var isNext = false

    private var isRefreshing = false
    private var isRefreshingForModifiedComment = false
    private var refreshingCommentsUntil = 0

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
                    if (response.bookmark_id == 0) {
                        setBookmarkButton(false)
                    } else {
                        setBookmarkButton(true)
                    }
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
            },
            handleError = {}
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
                if (isRefreshing) {
                    view.setRefreshing(false)
                    isRefreshing = false
                }
                if (isRefreshingForModifiedComment) {
                    if (refreshingCommentsUntil > 1) {
                        loadMoreComments()
                    } else {
                        view.run {
                            setTransparentRefreshingLayer(false)
                            getRecyclerViewState()
                        }
                        isRefreshingForModifiedComment = false
                    }
                }
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

    override fun refresh() {
        isRefreshing = true
        loadPostDetail()
    }

    override fun refreshForModifiedComment() {
        if (commentsRepository.isCommentModified()) {
            commentsRepository.saveIsCommentModified(false)
            isRefreshingForModifiedComment = true
            view.setTransparentRefreshingLayer(true)
            loadPostDetail()
        }
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
                    if (isRefreshingForModifiedComment) {
                        if (refreshingCommentsUntil > page) {
                            loadMoreComments()
                        } else {
                            view.run {
                                setTransparentRefreshingLayer(false)
                                getRecyclerViewState()
                            }
                            isRefreshingForModifiedComment = false
                        }
                    }
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
            onSuccess = { response ->
                postDetails.bookmark_id = response.bookmark_id
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
            },
            handleError = {}
        )
    }

    override fun deleteBookmark() {
        bookmarksRepository.deleteBookmark(
            DeleteBookmarkRequest(postDetails.bookmark_id),
            onSuccess = {
                postDetails.bookmark_id = 0
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
            },
            handleError = {}
        )
    }


    override fun openMenu(v: View) {
        view.showPopupMenu(v)
    }

    override fun openDeletePost() {
        if (userId == postDetails.user_id) {
            view.showDeletePostDialog()
        } else {
            view.showDeletePostFailedDialog()
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
            },
            handleError = {}
        )
    }

    override fun modifyPost() {
        if (userId == postDetails.user_id) {
            if (postDetails.liked != 0 || postDetails.disliked != 0) {
                view.showCannotModifyRatedPostDialog()
            } else {
                view.openEditPostUi(postId)
            }
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
            },
            handleError = {}
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
            },
            handleError = {}
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
                view.run {
                    hideSoftInput()
                    showErrorToast(
                        "댓글 작성에 실패했습니다" +
                                "\n ${NetworkUtil.getErrorMessage(e)}"
                    )
                }
            }
        )
    }

    override fun openCommentMenu(comment: Comment, commentIndex: Int) {
        this.tempComment = comment
        this.tempCommentIndex = commentIndex
        view.showCommentMenuDialog()
    }

    override fun openDeleteComment() {
        if (userId == tempComment.user_id) {
            view.showDeleteCommentDialog()
        } else {
            view.showDeleteCommentFailedDialog()
        }
    }

    override fun deleteComment() {
        commentsRepository.deleteComment(
            postId,
            tempComment.comment_id,
            onSuccess = {
                view.deleteComment(tempCommentIndex)
            },
            onFailure = { e ->
                view.apply {
                    showErrorToast(
                        "댓글 삭제에 실패했습니다" +
                                "\n ${NetworkUtil.getErrorMessage(e)}"
                    )
                }
            }
        )
    }

    override fun openModifyComment() {
        if (userId == tempComment.user_id) {
            refreshingCommentsUntil = page
            view.openModifyCommentUi(postId, tempComment)
        } else {
            view.showEditCommentFailedDialog()
        }
    }

}