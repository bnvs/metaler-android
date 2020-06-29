package com.bnvs.metaler.util.base.posts

import android.content.Intent
import androidx.lifecycle.Observer
import com.bnvs.metaler.util.base.tap.BaseTapActivity
import com.bnvs.metaler.view.detail.ActivityDetail

abstract class BasePostsActivity<VM : BasePostsViewModel> : BaseTapActivity<VM>() {

    override fun observeViewModel() {
        super.observeViewModel()
        observeStartDetailActivity()
    }

    private fun observeStartDetailActivity() {
        viewModel.openDetailActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    val postId = viewModel.postId.value
                    if (postId == null) {
                        makeToast("상세게시물을 볼 수 없습니다")
                    } else {
                        startDetailActivity(postId)
                    }
                }
            }
        )
    }

    private fun startDetailActivity(postId: Int) {
        Intent(this, ActivityDetail::class.java)
            .apply { putExtra("POST_ID", postId) }
            .also { startActivity(it) }
    }

}