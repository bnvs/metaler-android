package com.bnvs.metaler.ui.materials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.util.PostAdapter
import com.bnvs.metaler.util.PostItemListener
import kotlinx.android.synthetic.main.activity_materials.*
import kotlinx.android.synthetic.main.item_materials_category_rv.view.*
import kotlinx.android.synthetic.main.item_posts_rv.view.*

class ActivityMaterials : AppCompatActivity(),
    ContractMaterials.View {

    private val TAG = "ActivityMaterials"

    override lateinit var presenter: ContractMaterials.Presenter

    /**
     * 재료 탭의 카테고리 리사이클러뷰 아이템에 달아줄 리스너입니다
     * */
    private var categoryItemListener: CategoryItemListener = object :
        CategoryItemListener {
        override fun onCategoryClick(categoryType: String, position: Int) {
            if (categoryAdapter.selectedPosition != position) {
                categoryAdapter.also {
                    it.selectedPosition = position
                    it.notifyDataSetChanged()
                }
                // TODO : 카테고리 타입에 맞는 post 를 불러오도록 presenter 수정해야함
                presenter.loadPosts()
            }
        }
    }

    /**
     * 재료 탭에서 보여지는 재료 게시물 리사이클러뷰 아이템에 달아줄 리스너입니다
     * onPostClick -> 게시물을 클릭한 경우
     * onBookmarkButtonClick -> 북마크 버튼을 클릭한 경우
     * */
    private var itemListener: PostItemListener = object : PostItemListener {
        override fun onPostClick(clickedPostId: Int) {
            presenter.openPostDetail(clickedPostId)
        }

        override fun onBookmarkButtonClick(view: View, clickedPostId: Int, isBookmark: Boolean, position: Int) {
            if (!isBookmark) {
                view.bookmarkBtn.setImageResource(R.drawable.ic_list_bookmark_active_x3)
                presenter.addBookmark(clickedPostId)
                postAdapter.apply {
                    setBookmark(position)
                    notifyDataSetChanged()
                }
            }else {
                view.bookmarkBtn.setImageResource(R.drawable.ic_list_bookmark_inactive_x3)
                presenter.deleteBookmark(clickedPostId)
                postAdapter.apply {
                    setBookmark(position)
                    notifyDataSetChanged()
                }
            }
        }

    }

    private val postAdapter = PostAdapter(ArrayList(0), itemListener)
    private val postLayoutManager = LinearLayoutManager(this)

    private val categoryAdapter = CategoryAdapter(
        ArrayList(0),
        categoryItemListener
    )
    private val categoryLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materials)

        // Create the presenter
        presenter = PresenterMaterials(this, this)

        // Set up Buttons
        initClickListeners()

        // Set up categories recyclerView
        materialsCategoryRV.apply {
            adapter = categoryAdapter
            layoutManager = categoryLayoutManager
        }

        // Set up posts recyclerView
        postsRV.apply {
            adapter = postAdapter
            layoutManager = postLayoutManager
        }

        // Set up RefreshListener
        /*refreshLayout.apply {
            setOnRefreshListener {
                presenter.refreshPosts()
                refreshLayout.isRefreshing = false
            }
            setColorSchemeColors(
                ContextCompat.getColor(this@ActivityMaterials, R.color.colorPurple)
            )
        }*/

        // 재료 탭 presenter 시작
//        presenter.run {
//            start()
//        }

    }

    override fun showCategories() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPosts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPostDetailUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearchUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearchTags() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearSearchTagBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteSearchTag() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initClickListeners() {
        setTitleBarButtons()
        setTapBarButtons()
    }

    private fun setTitleBarButtons() {
        // 글작성, 글검색 버튼 클릭 리스너 달아주기
    }

    private fun setTapBarButtons() {
        homeBtn.setOnClickListener { presenter.openHome(this, this) }
        materialsBtn.setOnClickListener { presenter.openMaterials(this, this) }
        manufactureBtn.setOnClickListener { presenter.openManufactures(this, this) }
        bookmarkBtn.setOnClickListener { presenter.openBookmarks(this, this) }
        myPageBtn.setOnClickListener { presenter.openMyPage(this, this) }
    }

    /**
     * 재료 탭의 카테고리 리사이클러뷰에 사용할 어댑터입니다.
     * */
    private class CategoryAdapter(
        private var categories: List<Category>,
        private val itemListener: CategoryItemListener
    ) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

        var selectedPosition: Int = 0

        fun setCategories(list: List<Category>) {
            this.categories = list
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_materials_category_rv, parent, false)
            return ViewHolder(inflatedView)
        }

        override fun getItemCount(): Int {
            return categories.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(categories[position], position)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private var view: View = itemView

            fun bind(item: Category, position: Int) {

                view.apply {
                    materialsCategoryBtn.apply {
                        text = item.name
                        if (selectedPosition == position) {
                            setTextColor(ContextCompat.getColor(this.context ,R.color.colorPurple))
                            setBackgroundResource(R.drawable.active_bar)
                        }else {
                            setTextColor(ContextCompat.getColor(this.context ,R.color.colorLightGrey))
                            setBackgroundResource(0)
                        }
                    }
                    setOnClickListener { itemListener.onCategoryClick(item.name, position) }
                }

            }
        }
    }

    private interface CategoryItemListener {
        fun onCategoryClick(categoryType: String, position: Int)
    }

}
