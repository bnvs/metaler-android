package com.bnvs.metaler.ui.detail

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.comments.model.Comment
import com.bnvs.metaler.data.postdetails.model.PostDetails
import com.bnvs.metaler.ui.detail.adapter.PostDetailAdapter
import com.bnvs.metaler.ui.detail.listener.CommentMenuListener
import com.bnvs.metaler.ui.detail.listener.PostRatingListener
import com.bnvs.metaler.ui.postfirst.ActivityPostFirst
import kotlinx.android.synthetic.main.activity_detail.*


//재료탭, 가공탭의 상세페이지 레이아웃이 같기 떄문에 같이 사용한다.
class ActivityDetail : AppCompatActivity(), ContractDetail.View {

    private val TAG = "ActivityDetail"

    override lateinit var presenter: ContractDetail.Presenter
    private lateinit var postDetailAdapter: PostDetailAdapter

    private val postRatingListener = object :
        PostRatingListener {
        override fun onLikeButtonClick() {
            presenter.likePost()
        }

        override fun onDislikeButtonClick() {
            presenter.dislikePost()
        }
    }

    private val commentMenuListener = object : CommentMenuListener {
        override fun onClickMenuButton(comment: Comment, commentIndex: Int) {
            presenter.openCommentMenu(comment, commentIndex)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val postId = intent.getIntExtra("POST_ID", -1)
        Log.d(TAG, "intent 로 들어온 postId : $postId")
        if (postId == -1) {
            makeToast("잘못된 접근입니다")
            finish()
        }

        presenter = PresenterDetail(postId, this, this)

        initClickListeners()
        presenter.run {
            start()
        }

    }

    override fun initPostDetailAdapter(postDetails: PostDetails) {
        postDetailAdapter =
            PostDetailAdapter(postDetails, postRatingListener, commentMenuListener)
        postDetailRv.adapter = postDetailAdapter
    }

    override fun initPostDetailScrollListener() {
        postDetailRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = postDetailRv.layoutManager
                if (presenter.hasNextPage()) {
                    val lastVisibleItem = (layoutManager as LinearLayoutManager)
                        .findLastCompletelyVisibleItemPosition()
                    if (layoutManager.itemCount <= lastVisibleItem + 5) {
                        presenter.loadMoreComments()
                        presenter.setHasNextPage(false)
                    }
                }
            }
        })
    }

    override fun showComments(comments: List<Comment>) {
        postDetailAdapter.setComments(comments)
    }

    override fun setCommentsLoading(b: Boolean) {
        postDetailAdapter.setCommentsLoadingView(b)
    }

    override fun addComments(comments: List<Comment>) {
        postDetailAdapter.addComments(comments)
    }

    override fun addComment(comment: Comment) {
        postDetailAdapter.addComment(comment)
    }

    override fun deleteComment(commentIndex: Int) {
        postDetailAdapter.deleteComment(commentIndex)
    }

    override fun showPopupMenu(v: View) {
        Log.d(TAG, "옵션 메뉴 클릭")
        val menu = PopupMenu(this@ActivityDetail, v)
        menuInflater.inflate(R.menu.menu, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.modify -> presenter.modifyPost()
                R.id.delete -> presenter.openDeletePost()
            }
            return@setOnMenuItemClickListener false
        }
        menu.show()
    }

    override fun showDeletePostDialog() {
        AlertDialog.Builder(this@ActivityDetail)
            .setTitle("게시글을 삭제하시겠습니까?")
            .setPositiveButton("확인") { dialog, which ->
                presenter.deletePost()
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    override fun showCannotModifyRatedPostDialog() {
        makeAlertDialog("가격 평가가 진행된 게시물은 수정할 수 없습니다")
    }

    override fun showDeletePostFailedDialog() {
        makeAlertDialog("타인이 작성한 게시물은 삭제할 수 없습니다")
    }

    override fun showPostDeletedToast() {
        makeToast("게시물이 삭제되었습니다")
    }

    override fun finishActivity() {
        finish()
    }

    override fun showBookmarkAddDialog() {
        AlertDialog.Builder(this@ActivityDetail)
            .setTitle("이 글을 북마크하시겠습니까?")
            .setPositiveButton("확인") { dialog, which ->
                presenter.addBookmark()
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    override fun showBookmarkDeleteDialog() {
        AlertDialog.Builder(this@ActivityDetail)
            .setTitle("북마크를 취소하시겠습니까?")
            .setPositiveButton("확인") { dialog, which ->
                presenter.deleteBookmark()
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    override fun showBookmarkAddedToast() {
        makeToast("북마크에 추가되었습니다")
    }

    override fun showBookmarkDeletedToast() {
        makeToast("북마크가 취소되었습니다")
    }

    override fun openEditPostUi(postId: Int) {
        Intent(this, ActivityPostFirst::class.java).apply {
            putExtra("POST_ID", postId.toString())
            startActivity(this)
        }
    }

    override fun showEditPostFailedDialog() {
        makeAlertDialog("타인이 작성한 게시물은 수정할 수 없습니다")
    }

    override fun likePost() {
        postDetailAdapter.likePost()
    }

    override fun cancelLikePost() {
        postDetailAdapter.cancelLikePost()
    }

    override fun dislikePost() {
        postDetailAdapter.dislikePost()
    }

    override fun cancelDislikePost() {
        postDetailAdapter.cancelDislikePost()
    }

    private fun initClickListeners() {
        setTitleBarButtons()
        setCommentInputListener()
        setCommentRegisterButton()
    }

    override fun setBookmarkButton(b: Boolean) {
        if (b) {
            bookmarkBtn.isChecked = b
        } else {
            bookmarkBtn.isChecked = b
        }
    }

    private fun setTitleBarButtons() {
        backBtn.setOnClickListener { finish() }
        bookmarkBtn.setOnClickListener {
            if (bookmarkBtn.isChecked) {
                bookmarkBtn.isChecked = false
                presenter.openAddBookmark()
            } else {
                bookmarkBtn.isChecked = true
                presenter.openDeleteBookmark()
            }
        }
        moreBtn.setOnClickListener { v ->
            presenter.openMenu(v)
        }
    }

    private fun setCommentInputListener() {
        commentInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!commentInput.text.isNullOrBlank()) {
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
            if (!commentInput.text.isNullOrBlank()) {
                presenter.addComment(commentInput.text.toString())
            }
        }
    }

    override fun clearCommentInput() {
        commentInput.text.clear()
    }

    override fun showCommentMenuDialog() {
        val array = arrayOf("댓글 수정", "댓글 삭제")
        AlertDialog.Builder(this@ActivityDetail)
            .setItems(array) { _, which ->
                when (array[which]) {
                    "댓글 수정" -> {
                        presenter.setModifyComment()
                    }
                    "댓글 삭제" -> {
                        presenter.openDeleteComment()
                    }
                }
            }
            .show()
    }

    override fun showDeleteCommentDialog() {
        AlertDialog.Builder(this@ActivityDetail)
            .setTitle("게시글을 삭제하시겠습니까?")
            .setPositiveButton("확인") { dialog, which ->
                presenter.deleteComment()
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    override fun showDeleteCommentFailedDialog() {
        makeAlertDialog("타인이 작성한 댓글은 삭제할 수 없습니다")
    }

    override fun showCommentToModify(comment: String) {
        commentInput.setText(comment)
    }

    override fun showEditCommentFailedDialog() {
        makeAlertDialog("타인이 작성한 댓글은 수정할 수 없습니다")
    }

    override fun showSoftInput() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(commentInput, 0)
    }

    override fun hideSoftInput() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(commentInput.windowToken, 0)
    }

    override fun scrollToEnd() {
        postDetailRv.post {
            postDetailRv.scrollToPosition(postDetailRv.adapter!!.itemCount - 1)
        }
    }

    override fun showAlreadyRatedDialog() {
        makeAlertDialog("이미 평가한 게시물입니다")
    }

    override fun showErrorToast(errorMessage: String) {
        makeToast(errorMessage)
    }

    private fun makeToast(message: String) {
        Toast.makeText(this@ActivityDetail, message, Toast.LENGTH_LONG).show()
    }

    private fun makeAlertDialog(message: String) {
        AlertDialog.Builder(this@ActivityDetail)
            .setTitle("알림")
            .setMessage(message)
            .setPositiveButton("확인") { _, _ ->
            }
            .show()
    }
}
