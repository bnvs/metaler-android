package com.bnvs.metaler.view.bookmarks

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.bookmarks.model.Bookmark
import com.bnvs.metaler.databinding.ActivityBookmarkBinding
import com.bnvs.metaler.util.base.postsrv.BasePostsRvActivity
import com.bnvs.metaler.view.bookmarks.recyclerview.BookmarkClickListener
import com.bnvs.metaler.view.bookmarks.recyclerview.BookmarksAdapter
import kotlinx.android.synthetic.main.activity_bookmark.*
import org.koin.android.ext.android.inject

class ActivityBookmarks : BasePostsRvActivity<ViewModelBookmarks, Bookmark>() {

    private val TAG = "ActivityBookmarks"

    override val viewModel: ViewModelBookmarks by inject()

    private val bookmarksAdapter = BookmarksAdapter(object : BookmarkClickListener {
        override fun onPostClick(postId: Int) {
            viewModel.openPostDetail(postId)
        }

        override fun deleteBookmarkButtonClick(bookmarkId: Int, position: Int) {
            viewModel.deleteBookmark(bookmarkId, position)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityBookmarkBinding>(
            this,
            R.layout.activity_bookmark
        ).apply {
            vm = viewModel
            lifecycleOwner = this@ActivityBookmarks
            bookmarkRV.adapter = bookmarksAdapter
        }
        observeViewModel()
        setListeners()
    }

    override fun setRecyclerViewScrollListener() {
        bookmarkRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = bookmarkRV.layoutManager
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

    private var firstTime: Long = 0
    private var secondTime: Long = 0
    override fun onBackPressed() {
        secondTime = System.currentTimeMillis()
        makeToast("뒤로버튼을 한번 더 누르시면 종료됩니다")
        if (secondTime - firstTime < 2000) {
            super.onBackPressed()
            finishAffinity()
        }
        firstTime = System.currentTimeMillis()
    }
}
