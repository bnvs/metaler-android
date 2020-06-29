package com.bnvs.metaler.view.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.databinding.ActivitySearchBinding
import com.bnvs.metaler.util.base.postsrvadvanced.BasePostsRvAdvancedActivity
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.ext.android.inject

class ActivitySearch : BasePostsRvAdvancedActivity<ViewModelSearch, Post>() {

    override val viewModel: ViewModelSearch by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivitySearchBinding>(
            this,
            R.layout.activity_search
        ).apply {
            vm = viewModel
            lifecycleOwner = this@ActivitySearch
            postsRV.adapter = postsAdapter
        }

        observeViewModel()
        setListeners()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        observeFinishActivity()
    }

    override fun setRefreshListener() {}

    override fun setTagSearchTextView() {
        searchInput.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchWithContent()
                v.clearFocus()
                hideSoftInput(v)
                true
            } else {
                false
            }
        }
    }

    override fun setRecyclerViewScrollListener() {
        postsRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = postsRV.layoutManager
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

    private fun observeFinishActivity() {
        viewModel.finishActivity.observe(
            this,
            Observer { finishActivity ->
                if (finishActivity) {
                    finish()
                }
            }
        )
    }

    override fun observeLoading() {}
    override fun startPostFirstActivity() {}
    override fun startSearchActivity() {}
}
