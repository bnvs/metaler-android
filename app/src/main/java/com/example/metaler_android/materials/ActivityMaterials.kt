package com.example.metaler_android.materials

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.metaler_android.home.ActivityHome
import com.example.metaler_android.R
import com.example.metaler_android.bookmark.ActivityBookmark
import com.example.metaler_android.data.categories.Category
import com.example.metaler_android.manufactures.ActivityManufactures
import com.example.metaler_android.mypage.ActivityMyPage
import com.example.metaler_android.util.PostAdapter
import com.example.metaler_android.util.PostItemListener
import kotlinx.android.synthetic.main.activity_materials.*
import kotlinx.android.synthetic.main.item_materials_category_rv.view.*
import kotlinx.android.synthetic.main.item_posts_rv.view.*

class ActivityMaterials : AppCompatActivity(), ContractMaterials.View {

    private val TAG = "ActivityMaterials"

    override lateinit var presenter: ContractMaterials.Presenter

    /**
     * 재료 탭의 카테고리 리사이클러뷰 아이템에 달아줄 리스너입니다
     * */
    private var categoryItemListener: CategoryItemListener = object : CategoryItemListener {
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

    private val categoryAdapter = CategoryAdapter(ArrayList(0), categoryItemListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materials)

        // Create the presenter
        presenter = PresenterMaterials(
            this@ActivityMaterials,
            this@ActivityMaterials
        )

        // Set up Buttons
        initClickListeners()

        // Set up posts recyclerView
        postsRV.apply {
            adapter = postAdapter
            layoutManager = postLayoutManager
        }

        // 재료 탭 presenter 시작
        presenter.run {
            start()
        }

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

    /**
     * TapBarContract.View 에서 상속받은 함수
     * showHomeUi() ~ showMyPageUi() 까지
     * */
    override fun showHomeUi() {
        Intent(this@ActivityMaterials, ActivityHome::class.java).also {
            startActivity(it)
        }
    }

    override fun showMaterialsUi() {
        Intent(this@ActivityMaterials, ActivityMaterials::class.java).also {
            startActivity(it)
        }
    }

    override fun showManufacturesUi() {
        Intent(this@ActivityMaterials, ActivityManufactures::class.java).also {
            startActivity(it)
        }
    }

    override fun showBookmarksUi() {
        Intent(this@ActivityMaterials, ActivityBookmark::class.java).also {
            startActivity(it)
        }
    }

    override fun showMyPageUi() {
        Intent(this@ActivityMaterials, ActivityMyPage::class.java).also {
            startActivity(it)
        }
    }

    private fun initClickListeners() {
        setTitleBarButtons()
        setTapBarButtons()
    }

    private fun setTitleBarButtons() {
        // 글작성, 글검색 버튼 클릭 리스너 달아주기
    }

    private fun setTapBarButtons() {
        homeBtn.setOnClickListener { presenter.openHome() }
        materialsBtn.setOnClickListener { presenter.openMaterials() }
        manufactureBtn.setOnClickListener { presenter.openManufactures() }
        bookmarkBtn.setOnClickListener { presenter.openBookmarks() }
        myPageBtn.setOnClickListener { presenter.openMyPage() }
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

            @SuppressLint("ResourceAsColor")
            fun bind(item: Category, position: Int) {

                view.materialsCategoryBtn.text = item.name
                view.setOnClickListener { itemListener.onCategoryClick(item.name, position) }

                if (selectedPosition == position) {
                    view.materialsCategoryBtn.setTextColor(R.color.colorPurple)
                    view.materialsCategoryBtn.setBackgroundResource(R.drawable.active_bar)
                }else {
                    view.materialsCategoryBtn.setTextColor(R.color.colorLightGrey)
                    view.materialsCategoryBtn.setBackgroundResource(0)
                }
            }
        }
    }

    private interface CategoryItemListener {
        fun onCategoryClick(categoryType: String, position: Int)
    }

}
