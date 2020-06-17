package com.bnvs.metaler.view.materials

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.databinding.ActivityMaterialsBinding
import com.bnvs.metaler.util.BaseActivity
import com.bnvs.metaler.util.newadpaters.PostClickListener
import com.bnvs.metaler.util.newadpaters.PostsAdapter
import com.bnvs.metaler.util.newadpaters.TagClickListener
import com.bnvs.metaler.util.newadpaters.TagsAdapter
import com.bnvs.metaler.view.detail.ActivityDetail
import com.bnvs.metaler.view.materials.category.CategoriesAdapter
import com.bnvs.metaler.view.materials.category.CategoryClickListener
import com.bnvs.metaler.view.postfirst.ActivityPostFirst
import com.bnvs.metaler.view.search.ActivitySearch
import kotlinx.android.synthetic.main.activity_materials.*
import org.koin.android.ext.android.inject

class ActivityMaterials : BaseActivity<ViewModelMaterials>() {

    override val viewModel: ViewModelMaterials by inject()

    private val categoriesAdapter = CategoriesAdapter(object : CategoryClickListener {
        override fun onCategoryClick(categoryId: Int) {
            viewModel.changeSelectedCategory(categoryId)
            Log.d("dd", "카테고리 아이디 : $categoryId")
        }
    })
    private val tagsAdapter = TagsAdapter(object : TagClickListener {
        override fun onTagClick(position: Int) = viewModel.removeSearchWord(position)
    })
    private val postsAdapter = PostsAdapter(object : PostClickListener {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMaterialsBinding>(
            this,
            R.layout.activity_materials
        ).apply {
            vm = viewModel
            lifecycleOwner = this@ActivityMaterials
            materialsCategoryRV.adapter = categoriesAdapter
            tagRV.adapter = tagsAdapter
            postsRV.adapter = postsAdapter
        }

        observeViewModel()
        setListeners()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        observeStartDetailActivity()
        observeStartPostFirstActivity()
        observeStartSearchActivity()
        observeLoading()
    }

    private fun setListeners() {
        setRefreshListener()
        setTagSearchTextView()
        setRecyclerViewScrollListener()
    }

    private fun setRefreshListener() {
        refreshLayout.setOnRefreshListener { viewModel.refresh() }
    }

    private fun setTagSearchTextView() {
        tagInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.addSearchWord()
                true
            } else {
                false
            }
        }
    }

    private fun setRecyclerViewScrollListener() {
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

    private fun observeLoading() {
        viewModel.isLoading.observe(
            this,
            Observer { isLoading ->
                if (refreshLayout.isRefreshing && !isLoading) {
                    refreshLayout.isRefreshing = false
                }
            }
        )
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

    private fun startDetailActivity(postId: Int) {
        Intent(this, ActivityDetail::class.java)
            .apply { putExtra("POST_ID", postId) }
            .also { startActivity(it) }

        overridePendingTransition(0, 0)
    }

    private fun startPostFirstActivity() {
        Intent(this, ActivityPostFirst::class.java)
            .apply {
                putExtra("CATEGORY_TYPE", "MATERIALS")
                putExtra("TYPE", "ADD_POST")
            }.also { startActivity(it) }

        overridePendingTransition(0, 0)
    }

    private fun startSearchActivity() {
        Intent(this, ActivitySearch::class.java)
            .apply { putExtra("CATEGORY_TYPE", "MATERIALS") }
            .also { startActivity(it) }

        overridePendingTransition(0, 0)
    }

}