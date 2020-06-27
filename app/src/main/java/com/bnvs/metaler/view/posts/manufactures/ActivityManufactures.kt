package com.bnvs.metaler.view.posts.manufactures

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.databinding.ActivityManufactureBinding
import com.bnvs.metaler.util.base.postsrvadvanced.BasePostsRvAdvancedActivity
import com.bnvs.metaler.view.addeditpost.postfirst.ActivityPostFirst
import com.bnvs.metaler.view.search.ActivitySearch
import kotlinx.android.synthetic.main.activity_manufacture.*
import org.koin.android.ext.android.inject

class ActivityManufactures : BasePostsRvAdvancedActivity<ViewModelManufactures, Post>() {

    override val viewModel: ViewModelManufactures by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityManufactureBinding>(
            this,
            R.layout.activity_manufacture
        ).apply {
            vm = viewModel
            lifecycleOwner = this@ActivityManufactures
            tagRV.adapter = tagsAdapter
            postsRV.adapter = postsAdapter
            tagInput.let {
                it.setAdapter(getHashTagSuggestAdapter())
                it.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (!it.text.isNullOrBlank()) {
                            viewModel.getTagSuggestions(it.text.toString())
                        }
                    }
                })
            }
        }

        observeViewModel()
        setListeners()
    }

    override fun setRefreshListener() {
        refreshLayout.setOnRefreshListener { viewModel.refresh() }
    }

    override fun setTagSearchTextView() {
        tagInput.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.addSearchWord()
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

    override fun observeLoading() {
        viewModel.isLoading.observe(
            this,
            Observer { isLoading ->
                if (refreshLayout.isRefreshing && !isLoading) {
                    refreshLayout.isRefreshing = false
                }
            }
        )
    }

    override fun startPostFirstActivity() {
        Intent(this, ActivityPostFirst::class.java)
            .also { startActivity(it) }

        overridePendingTransition(0, 0)
    }

    override fun startSearchActivity() {
        Intent(this, ActivitySearch::class.java)
            .also { startActivity(it) }

        overridePendingTransition(0, 0)
    }
}
