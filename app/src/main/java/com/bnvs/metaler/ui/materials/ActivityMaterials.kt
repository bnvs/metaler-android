package com.bnvs.metaler.ui.materials

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.categories.Category
import com.bnvs.metaler.data.posts.Post
import com.bnvs.metaler.util.EndlessRecyclerViewScrollListener
import com.bnvs.metaler.util.PostAdapter
import kotlinx.android.synthetic.main.activity_manufacture.*
import kotlinx.android.synthetic.main.activity_materials.*
import kotlinx.android.synthetic.main.activity_materials.bookmarkBtn
import kotlinx.android.synthetic.main.activity_materials.homeBtn
import kotlinx.android.synthetic.main.activity_materials.manufactureBtn
import kotlinx.android.synthetic.main.activity_materials.materialsBtn
import kotlinx.android.synthetic.main.activity_materials.myPageBtn
import kotlinx.android.synthetic.main.activity_materials.postsRV
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


    lateinit var posts: List<Post>
    lateinit var postAdapter: PostAdapter
    lateinit var scrollListener: EndlessRecyclerViewScrollListener
    lateinit var postLayoutManager: RecyclerView.LayoutManager
    var loadMorePosts: java.util.ArrayList<Post?> = java.util.ArrayList()


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

    private fun setRVLayoutManager() {
        postLayoutManager = LinearLayoutManager(this)
        postsRV.layoutManager = postLayoutManager
        postsRV.setHasFixedSize(true)
    }

    override fun setRVScrollListener() {
        postLayoutManager = LinearLayoutManager(this)
        scrollListener =
            EndlessRecyclerViewScrollListener(postLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            EndlessRecyclerViewScrollListener.OnLoadMoreListener {
            override fun onLoadMore() {
                Log.d(TAG, "스크롤리스너 onLoadMore() 실행! ")


                //loadMorePosts 에 null값을 추가해서 로딩뷰를 만든다.
                postAdapter.addLoadingView()
                Log.d(TAG, "postAdapter.addLoadingView() 후 tempArrayList ? : ${postAdapter.tempArrayList}")
                loadMorePosts.add(null) // 이렇게 넣어줘야 하나 ?.. 어댑터랑 연결해서 넣어줄 수 없나

//                Log.d(TAG, "addLoadingView 실행 후 loadMorePosts 값 ? : ${loadMorePosts}")

                //loadMorePosts 는 다음페이지 데이터를 받아올 때만 데이터를 추가하기 때문에 조건절로 비어있는지 확인해야함
                if (!loadMorePosts.isEmpty()) {
                    //loadMorePosts의 마지막 값이 null값이 있으면 무한스크롤 로딩 중이기 때문에 데이터를 받아오고, 로딩뷰를 제거한다.
                    if (loadMorePosts[loadMorePosts.size - 1] == null) {
                        Log.d(TAG, "loadMorePosts 마지막 아이템에 null값 있음 ")
                        presenter.loadMorePosts(presenter.requestPosts())
//                        showMorePosts()
                    }
                }

            }
        })
        postsRV.addOnScrollListener(scrollListener)
    }


    override fun showPosts(posts: List<Post>) {
        postAdapter = PostAdapter(itemListener)
        postAdapter.addPosts(posts)
        postAdapter.notifyDataSetChanged()
        postsRV.adapter = postAdapter
    }

    override fun showMorePosts(posts: List<Post>) {

        if(loadMorePosts[loadMorePosts.size-1] == null) {
            //Use Handler if the items are loading too fast.
            //If you remove it, the data will load so fast that you can't even see the LoadingView
            Handler().postDelayed({
                //Remove the Loading View
                postAdapter.removeLoadingView()

                //loadMorePosts에 있던 값을 다 지우고 추가로 받은 데이터 넣음
                loadMorePosts.removeAll(loadMorePosts)
                loadMorePosts.addAll(posts)

                //We adding the data to our main ArrayList
                postAdapter.addMorePosts(loadMorePosts)
                //Change the boolean isLoading to false
                scrollListener.setLoaded()
                //Update the recyclerView in the main thread
                postsRV.post {
                    postAdapter.notifyDataSetChanged()
                }
            }, 1000)
        }

    }


    override fun showCategories() {
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
