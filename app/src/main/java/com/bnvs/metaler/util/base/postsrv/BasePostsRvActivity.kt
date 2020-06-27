package com.bnvs.metaler.util.base.postsrv

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bnvs.metaler.util.base.posts.BasePostsActivity
import com.bnvs.metaler.view.posts.recyclerview.adapter.PostsAdapter
import com.bnvs.metaler.view.posts.recyclerview.adapter.TagsAdapter
import com.bnvs.metaler.view.posts.recyclerview.listener.PostClickListener
import com.bnvs.metaler.view.posts.recyclerview.listener.TagClickListener

abstract class BasePostsRvActivity<VM : BasePostsRvViewModel> : BasePostsActivity<VM>() {

    protected val tagsAdapter =
        TagsAdapter(object :
            TagClickListener {
            override fun onTagClick(position: Int) = viewModel.removeSearchWord(position)
        })

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

    override fun onResume() {
        super.onResume()
        viewModel.refreshForOnResume()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        observeStartPostFirstActivity()
        observeStartSearchActivity()
        observeLoading()
    }

    protected fun setListeners() {
        setRefreshListener()
        setTagSearchTextView()
        setRecyclerViewScrollListener()
    }

    protected abstract fun setRefreshListener()
    protected abstract fun setTagSearchTextView()
    protected abstract fun setRecyclerViewScrollListener()

    protected abstract fun observeLoading()

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