package com.bnvs.metaler.view.myposts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.myposts.model.MyPost
import com.bnvs.metaler.databinding.ActivityMyPostsBinding
import com.bnvs.metaler.util.base.postsrv.BasePostsRvActivity
import com.bnvs.metaler.view.addeditpost.postfirst.ActivityPostFirst
import com.bnvs.metaler.view.myposts.recyclerview.MyPostClickListener
import com.bnvs.metaler.view.myposts.recyclerview.MyPostsAdapter
import kotlinx.android.synthetic.main.activity_my_posts.*
import org.koin.android.ext.android.inject

class ActivityMyPosts : BasePostsRvActivity<ViewModelMyPosts, MyPost>() {

    private val TAG = "ActivityMyPosts"

    override val viewModel: ViewModelMyPosts by inject()

    private val myPostsAdapter = MyPostsAdapter(object : MyPostClickListener {
        override fun onPostClick(postId: Int) {
            viewModel.openPostDetail(postId)
        }

        override fun onMoreButtonClick(postId: Int, position: Int) {
            viewModel.openMyPostMenuDialog(postId, position)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMyPostsBinding>(
            this,
            R.layout.activity_my_posts
        ).apply {
            vm = viewModel
            lifecycleOwner = this@ActivityMyPosts
            myPostsRV.adapter = myPostsAdapter
        }

        observeViewModel()
        setListeners()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        observeOpenMyPostMenuDialog()
        observeOpenPostFirstActivity()
        observeOpenPostDeleteDialog()
        observeFinishThisActivity()
    }

    private fun observeOpenMyPostMenuDialog() {
        viewModel.openMyPostMenuDialog.observe(
            this,
            Observer { openDialog ->
                if (openDialog.isNotEmpty()) {
                    openMyPostMenuDialog(
                        openDialog["postId"] as Int,
                        openDialog["position"] as Int
                    )
                }
            }
        )
    }

    private fun observeOpenPostFirstActivity() {
        viewModel.openPostFirstActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity.isNotEmpty()) {
                    openPostFirstActivity(startActivity["postId"] as Int)
                }
            }
        )
    }

    private fun observeOpenPostDeleteDialog() {
        viewModel.openPostDeleteDialog.observe(
            this,
            Observer { openDialog ->
                if (openDialog.isNotEmpty()) {
                    openPostDeleteDialog(
                        openDialog["postId"] as Int,
                        openDialog["position"] as Int
                    )
                }
            }
        )
    }

    private fun observeFinishThisActivity() {
        viewModel.finishThisActivity.observe(
            this,
            Observer { finish ->
                if (finish) {
                    finish()
                }
            }
        )
    }

    override fun setRecyclerViewScrollListener() {
        myPostsRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = myPostsRV.layoutManager
                if (viewModel.hasNextPage.value == true) {
                    val lastVisibleItem = (layoutManager as LinearLayoutManager)
                        .findLastCompletelyVisibleItemPosition()
                    if (layoutManager.itemCount <= lastVisibleItem + 5) {
                        viewModel.loadMorePosts()
                    }
                }
            }
        })
    }

    private fun openMyPostMenuDialog(postId: Int, position: Int) {
        val array = arrayOf("수정", "삭제")
        AlertDialog.Builder(this@ActivityMyPosts)
            .setItems(array) { _, which ->
                when (array[which]) {
                    "수정" -> {
                        viewModel.openPostFirstActivity(postId, position)
                    }
                    "삭제" -> {
                        viewModel.openDeletePostDialog(postId, position)
                    }
                }
            }
            .show()
    }

    private fun openPostFirstActivity(postId: Int) {
        Intent(this, ActivityPostFirst::class.java).apply {
            putExtra("POST_ID", postId)
            startActivity(this)
        }
    }

    private fun openPostDeleteDialog(postId: Int, position: Int) {
        AlertDialog.Builder(this@ActivityMyPosts)
            .setTitle("게시글을 삭제하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                viewModel.deletePost(postId, position)
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }
}
