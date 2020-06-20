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
import com.bnvs.metaler.util.base.postsrv.BasePostsRvActivity
import com.bnvs.metaler.util.posts.adapter.CategoriesAdapter
import com.bnvs.metaler.util.posts.listener.CategoryClickListener
import com.bnvs.metaler.view.postfirst.ActivityPostFirst
import com.bnvs.metaler.view.search.ActivitySearch
import kotlinx.android.synthetic.main.activity_materials.*
import org.koin.android.ext.android.inject

class ActivityMaterials : BasePostsRvActivity<ViewModelMaterials>() {

    override val viewModel: ViewModelMaterials by inject()

    private val categoriesAdapter =
        CategoriesAdapter(object :
            CategoryClickListener {
            override fun onCategoryClick(categoryId: Int) {
                viewModel.changeSelectedCategory(categoryId)
                Log.d("dd", "카테고리 아이디 : $categoryId")
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