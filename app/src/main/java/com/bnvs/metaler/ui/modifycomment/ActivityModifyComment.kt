package com.bnvs.metaler.ui.modifycomment

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bnvs.metaler.R
import com.bnvs.metaler.data.comments.model.Comment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.activity_modify_comment.*

class ActivityModifyComment : AppCompatActivity(), ContractModifyComment.View {

    override lateinit var presenter: ContractModifyComment.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_comment)

        val postId = intent.getIntExtra("POST_ID", -1)
        if (postId == -1) {
            makeToast("잘못된 접근입니다")
            finish()
        }
        val comment = intent.getSerializableExtra("COMMENT") as Comment

        presenter = PresenterModifyComment(postId, comment, this, this)

        initClickListeners()
        presenter.run {
            start()
        }
    }

    override fun showComment(comment: Comment) {
        userName.text = comment.nickname
        date.text = comment.date
        commentContent.text = comment.content

        Glide.with(this)
            .load(comment.profile_url)
            .error(R.drawable.ic_profile_x3)
            .override(200, 200)
            .transform(CircleCrop())
            .into(userImg)
    }

    override fun showModifiedComment(comment: String) {
        commentContent.text = comment
    }

    override fun showModifyCommentSuccessToast() {
        makeToast("댓글이 성공적으로 수정되었습니다")
    }

    override fun setCommentToModify(comment: String) {
        commentInput.setText(comment)
    }

    override fun setCurrentTitleText(text: String) {
        currentTitleText.text = text
    }

    override fun hideSoftInput() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(commentInput.windowToken, 0)
    }

    override fun showSoftInput() {
        commentInput.requestFocus()
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(commentInput, 0)
    }

    private fun initClickListeners() {
        setTitleBarButtons()
        setCommentInputListener()
        setCommentRegisterButton()
    }

    private fun setTitleBarButtons() {
        backBtn.setOnClickListener { finish() }
        currentTitleText.setOnClickListener { finish() }
    }

    private fun setCommentInputListener() {
        commentInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!commentInput.text.isNullOrBlank() && commentInput.text.toString() != presenter.content) {
                    commentRegisterBtn.setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.colorPurple
                        )
                    )
                    commentRegisterBtn.setTypeface(null, Typeface.BOLD)
                } else {
                    commentRegisterBtn.setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.colorGrey
                        )
                    )
                    commentRegisterBtn.setTypeface(null, Typeface.NORMAL)
                }
            }
        })
    }

    private fun setCommentRegisterButton() {
        commentRegisterBtn.setOnClickListener {
            if (!commentInput.text.isNullOrBlank() && commentInput.text.toString() != presenter.content) {
                presenter.modifyComment(commentInput.text.toString())
                commentInputCard.visibility = View.GONE
            }
        }
    }

    override fun showErrorToast(errorMessage: String) {
        makeToast(errorMessage)
    }

    private fun makeToast(message: String) {
        Toast.makeText(this@ActivityModifyComment, message, Toast.LENGTH_LONG).show()
    }
}
