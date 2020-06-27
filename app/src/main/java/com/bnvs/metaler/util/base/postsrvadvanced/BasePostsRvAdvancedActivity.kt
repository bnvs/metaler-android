package com.bnvs.metaler.util.base.postsrvadvanced

import android.R
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bnvs.metaler.util.base.postsrv.BasePostsRvActivity
import com.bnvs.metaler.view.addeditpost.postsecond.tagsuggest.HashTagSuggestAdapter
import com.bnvs.metaler.view.posts.recyclerview.adapter.PostsAdapter
import com.bnvs.metaler.view.posts.recyclerview.adapter.TagsAdapter
import com.bnvs.metaler.view.posts.recyclerview.listener.PostClickListener
import com.bnvs.metaler.view.posts.recyclerview.listener.TagClickListener

abstract class BasePostsRvAdvancedActivity<VM : BasePostsRvAdvancedViewModel<ITEM>, ITEM>
    : BasePostsRvActivity<VM, ITEM>() {

    protected val postsAdapter =
        PostsAdapter(object :
            PostClickListener {
            override fun onPostClick(postId: Int) {
                viewModel.openPostDetail(postId)
            }

            override fun addBookmarkButtonClick(postId: Int, position: Int) {
                viewModel.addBookmark(postId, position)
            }

            override fun deleteBookmarkButtonClick(bookmarkId: Int, position: Int) {
                viewModel.deleteBookmark(bookmarkId, position)
            }
        })

    protected val tagsAdapter =
        TagsAdapter(object :
            TagClickListener {
            override fun onTagClick(position: Int) = viewModel.removeSearchWord(position)
        })

    override fun observeViewModel() {
        super.observeViewModel()
        observeLoading()
        observeStartPostFirstActivity()
        observeStartSearchActivity()
    }

    override fun setListeners() {
        super.setListeners()
        setRefreshListener()
        setTagSearchTextView()
    }

    protected abstract fun observeLoading()

    protected abstract fun setRefreshListener()
    protected abstract fun setTagSearchTextView()

    protected fun getHashTagSuggestAdapter(): HashTagSuggestAdapter {
        return HashTagSuggestAdapter(this, R.layout.simple_list_item_1)
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshForOnResume()
    }

    private fun observeStartPostFirstActivity() {
        viewModel.openPostFirstActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    startPostFirstActivity()
                }
            }
        )
    }

    private fun observeStartSearchActivity() {
        viewModel.openSearchActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    startSearchActivity()
                }
            }
        )
    }

    protected abstract fun startPostFirstActivity()
    protected abstract fun startSearchActivity()

    protected fun hideSoftInput(view: TextView) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}