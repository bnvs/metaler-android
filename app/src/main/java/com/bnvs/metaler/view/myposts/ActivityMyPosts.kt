package com.bnvs.metaler.view.myposts

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.myposts.model.MyPost
import com.bnvs.metaler.databinding.ActivityMyPostsBinding
import com.bnvs.metaler.util.base.postsrv.BasePostsRvActivity
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

        override fun onMoreButtonClick(
            view: View,
            clickedPostId: Int,
            likedNum: Int,
            dislikedNum: Int,
            position: Int
        ) {

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
}
