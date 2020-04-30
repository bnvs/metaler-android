package com.bnvs.metaler.ui.detail

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.comments.model.Comment
import com.bnvs.metaler.data.postdetails.model.PostDetails

interface ContractDetail {
    interface View : BaseView<Presenter> {
        fun showEmptyPostIdToast()

        fun initPostDetailAdapter(postDetails: PostDetails)

        fun initPostDetailScrollListener()

        fun showComments(comments: List<Comment>)

        fun setCommentsLoading(b: Boolean)

        fun addComments(comments: List<Comment>)

        fun addComment(comment: Comment)

        fun deleteComment(commentIndex: Int)

        fun showOptionsMenu(v: android.view.View)

        fun openEditPostUi(postId: Int)

        fun likePost()

        fun cancelLikePost()

        fun dislikePost()

        fun cancelDislikePost()
    }

    interface Presenter : BasePresenter {
        fun loadPostDetail()

        fun loadComments()

        fun loadMoreComments()

        fun hasNextPage(): Boolean

        fun setHasNextPage(b: Boolean)

        fun addBookmark()

        fun deleteBookmark()

        fun openMenu(v: android.view.View)

        fun deletePost()

        fun modifyPost()

        fun likePost()

        fun dislikePost()
    }
}
