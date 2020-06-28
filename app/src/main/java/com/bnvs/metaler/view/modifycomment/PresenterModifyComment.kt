package com.bnvs.metaler.view.modifycomment

import com.bnvs.metaler.data.comments.model.AddEditCommentRequest
import com.bnvs.metaler.data.comments.model.Comment
import com.bnvs.metaler.data.comments.source.repository.CommentsRepository
import com.bnvs.metaler.network.NetworkUtil
import org.koin.core.KoinComponent
import org.koin.core.inject

class PresenterModifyComment(
    private val postId: Int,
    private val comment: Comment,
    private val view: ContractModifyComment.View
) : ContractModifyComment.Presenter, KoinComponent {

    private val commentsRepository: CommentsRepository by inject()
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