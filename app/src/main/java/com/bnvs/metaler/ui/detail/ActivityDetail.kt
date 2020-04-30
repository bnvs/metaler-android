package com.bnvs.metaler.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.comments.model.Comment
import com.bnvs.metaler.data.postdetails.model.PostDetails
import com.bnvs.metaler.ui.postfirst.ActivityPostFirst
import kotlinx.android.synthetic.main.activity_detail.*


//재료탭, 가공탭의 상세페이지 레이아웃이 같기 떄문에 같이 사용한다.
class ActivityDetail : AppCompatActivity(), ContractDetail.View {

    private val TAG = "ActivityDetail"

    override lateinit var presenter: ContractDetail.Presenter
    private lateinit var postDetailAdapter: PostDetailAdapter

    private val postRatingListener = object : PostRatingListener {
        override fun onLikeButtonClick() {
            presenter.likePost()
        }

        override fun onDislikeButtonClick() {
            presenter.dislikePost()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val postId = intent.getIntExtra("POST_ID", -1)
        Log.d(TAG, "intent 로 들어온 postId : $postId")
        if (postId == -1) {
            showEmptyPostIdToast()
            finish()
        }

        presenter = PresenterDetail(postId, this)

        initClickListeners()
        presenter.run {
            start()
        }

    }

    override fun initPostDetailAdapter(postDetails: PostDetails) {
        postDetailAdapter = PostDetailAdapter(postDetails, postRatingListener)
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

    override fun showOptionsMenu(v: View) {
        Log.d(TAG, "옵션 메뉴 클릭")
        val menu = PopupMenu(this@ActivityDetail, v)
        menuInflater.inflate(R.menu.menu, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.modify -> presenter.modifyPost()
                R.id.delete -> presenter.deletePost()
            }
            return@setOnMenuItemClickListener false
        }
        menu.show()
    }

    override fun openEditPostUi(postId: Int) {
        Intent(this, ActivityPostFirst::class.java).apply {
            putExtra("POST_ID", postId.toString())
            startActivity(this)
        }
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
    }

    private fun setTitleBarButtons() {
        backBtn.setOnClickListener { finish() }
        bookmarkBtn.setOnClickListener { }
        moreBtn.setOnClickListener { v ->
            presenter.openMenu(v)
        }
    }

    override fun showEmptyPostIdToast() {
        makeToast("게시물을 불러오는데 실패했습니다")
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
