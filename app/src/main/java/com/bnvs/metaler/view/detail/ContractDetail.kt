package com.bnvs.metaler.view.detail

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.comments.model.Comment
import com.bnvs.metaler.data.postdetails.model.PostDetails

interface ContractDetail {
    interface View : BaseView<Presenter> {
        fun getRecyclerViewState()
        fun setTransparentRefreshingLayer(b: Boolean)
        fun initPostDetailAdapter(postDetails: PostDetails)
        fun initPostDetailScrollListener()
        fun showComments(comments: List<Comment>)
        fun setCommentsLoading(b: Boolean)
        fun addComments(comments: List<Comment>)
        fun addComment(comment: Comment)
        fun deleteComment(commentIndex: Int)
        fun setBookmarkButton(b: Boolean)
        fun showPopupMenu(v: android.view.View)
        fun showDeletePostDialog()
        fun showCannotModifyRatedPostDialog()
        fun showDeletePostFailedDialog()
        fun showPostDeletedToast()
        fun finishActivity()
        fun showBookmarkAddDialog()
        fun showBookmarkDeleteDialog()
        fun showBookmarkAddedToast()
        fun showBookmarkDeletedToast()
        fun openEditPostUi(postId: Int)
        fun showEditPostFailedDialog()
        fun likePost()
        fun cancelLikePost()
        fun dislikePost()
        fun cancelDislikePost()
        fun showAlreadyRatedDialog()
        fun showErrorToast(errorMessage: String)
        fun setRefreshing(b: Boolean)
        fun clearCommentInput()
        fun hideSoftInput()
        fun scrollToEnd()
        fun showCommentMenuDialog()
        fun showDeleteCommentDialog()
        fun showDeleteCommentFailedDialog()
        fun openModifyCommentUi(postId: Int, comment: Comment)
        fun showEditCommentFailedDialog()
    }

    interface Presenter : BasePresenter {
        fun loadPostDetail()
        fun loadComments()
        fun refresh()
        fun refreshForModifiedComment()
        fun loadMoreComments()
        fun hasNextPage(): Boolean
        fun setHasNextPage(b: Boolean)
        fun addBookmark()
        fun deleteBookmark()
        fun openMenu(v: android.view.View)
        fun openDeletePost()
        fun openAddBookmark()
        fun openDeleteBookmark()
        fun deletePost()
        fun modifyPost()
        fun ratePost(rating: Int)
        fun likePost()
        fun dislikePost()
        fun unRatePost()
        fun addComment(comment: String)
        fun openCommentMenu(comment: Comment, commentIndex: Int)
        fun openDeleteComment()
        fun deleteComment()
        fun openModifyComment()
    }
}
