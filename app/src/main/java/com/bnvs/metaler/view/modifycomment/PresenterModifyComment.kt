package com.bnvs.metaler.view.modifycomment

import android.content.Context
import com.bnvs.metaler.data.comments.model.AddEditCommentRequest
import com.bnvs.metaler.data.comments.model.Comment
import com.bnvs.metaler.data.comments.source.repository.CommentsRepository
import com.bnvs.metaler.network.NetworkUtil

class PresenterModifyComment(
    private val postId: Int,
    private val comment: Comment,
    context: Context,
    private val view: ContractModifyComment.View
) : ContractModifyComment.Presenter {

    private val commentsRepository = CommentsRepository(context)
    override val content = comment.content

    override fun start() {
        view.run {
            showComment(comment)
            setCommentToModify(comment.content)
            showSoftInput()
        }
    }

    override fun modifyComment(comment: String) {
        commentsRepository.editComment(
            postId,
            this.comment.comment_id,
            AddEditCommentRequest(comment),
            onSuccess = {
                commentsRepository.saveIsCommentModified(true)
                view.run {
                    showModifyCommentSuccessToast()
                    showModifiedComment(comment)
                    setCurrentTitleText("돌아가기")
                    hideSoftInput()
                }
            },
            onFailure = { e ->
                view.apply {
                    showErrorToast(
                        "댓글 수정에 실패했습니다" +
                                "\n ${NetworkUtil.getErrorMessage(e)}"
                    )
                }
            }
        )
    }
}