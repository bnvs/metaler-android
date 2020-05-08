package com.bnvs.metaler.ui.materials

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.ui.postfirst.ActivityPostFirst
import com.bnvs.metaler.util.*
import kotlinx.android.synthetic.main.activity_materials.*
import kotlinx.android.synthetic.main.item_materials_category_rv.view.*

class ActivityMaterials : AppCompatActivity(),
    ContractMaterials.View {

    private val TAG = "ActivityMaterials"

    override lateinit var presenter: ContractMaterials.Presenter

    lateinit var posts: List<Post>
    lateinit var postAdapter: PostAdapter
    lateinit var scrollListener: EndlessRecyclerViewScrollListener
    lateinit var postLayoutManager: RecyclerView.LayoutManager
    var loadMorePosts: ArrayList<Post?> = ArrayList()

    var tagSearchWords: MutableList<String?> = mutableListOf()

    lateinit var tagSearchAdapter: TagSearchAdapter
    private val tagSearchLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

    /**
     * 재료 탭의 카테고리 리사이클러뷰 아이템에 달아줄 리스너입니다
     * */
    private var categoryItemListener: CategoryItemListener = object :
        CategoryItemListener {
        override fun onCategoryClick(categoryType: Int, position: Int) {
            if (categoryAdapter.selectedPosition != position) {
                categoryAdapter.also {
                    it.selectedPosition = position
                    it.notifyDataSetChanged()
                }
                presenter.resetPageNum()
                presenter.loadPosts(presenter.requestPosts(categoryType))
            }
        }
    }

    /**
     * 재료 탭의 태그 검색 리사이클러뷰 아이템에 달아줄 리스너입니다
     * */
    private var tagSearchItemListener: TagSearchItemListener = object :
        TagSearchItemListener {
        override fun onTagDeleteBtnClick(view: View, position: Int) {
            tagSearchAdapter.removeTag(position)
            if (position == 0) {
                tagRV.visibility = View.GONE
            }
        }
    }

    /**
     * 재료 탭에서 보여지는 재료 게시물 리사이클러뷰 아이템에 달아줄 리스너입니다
     * onPostClick -> 게시물을 클릭한 경우
     * onBookmarkButtonClick -> 북마크 버튼을 클릭한 경우
     * */
    private var itemListener: PostItemListener = object :
        PostItemListener {
        override fun onPostClick(view: View, clickedPostId: Int) {
            presenter.openPostDetail(clickedPostId)
        }

        override fun onBookmarkButtonClick(
            view: View,
            clickedPostId: Int,
            isBookmark: Int,
            position: Int
        ) {

            if (isBookmark == 0) {
                presenter.addBookmark(clickedPostId)
                postAdapter.apply {
                    setBookmark(position)
                    notifyDataSetChanged()
                    Log.d(TAG, "isBookmark ? : ${isBookmark}")

                }
            } else if (isBookmark == 1) {
                presenter.deleteBookmark(clickedPostId)
                postAdapter.apply {
                    setBookmark(position)
                    notifyDataSetChanged()
                    Log.d(TAG, "isBookmark ? : ${isBookmark}")

                }
            }
        }

    }


    private val categoryAdapter = CategoryAdapter(
        categoryItemListener
    )
    private val categoryLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materials)

        // Create the presenter
        presenter = PresenterMaterials(this, this)

        // 재료 탭 presenter 시작
        presenter.run {
            start()
        }

        // Set up Buttons
        initClickListeners()

        setRVAdapter()

        setRVLayoutManager()

        setRVScrollListener()

        onRefresh()

    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onRefresh() {
        refreshLayout.setOnRefreshListener {
            presenter.resetPageNum()
            presenter.loadPosts(presenter.requestPosts(presenter.getCategoryId()))
            // The method calls setRefreshing(false) when it's finished.
            refreshLayout.setRefreshing(false)
            scrollListener.setLoaded()
        }
    }

    override fun showRefreshPosts(posts: List<Post>) {
        postAdapter.resetList()
        postAdapter.addPosts(posts)
        postAdapter.notifyDataSetChanged()
    }

    private fun setRVLayoutManager() {
        //게시글 리사이클러뷰
        postLayoutManager = LinearLayoutManager(this)
        postsRV.layoutManager = postLayoutManager
        postsRV.setHasFixedSize(true)

        //카테고리 리사이클러뷰
        materialsCategoryRV.layoutManager = categoryLayoutManager

        //태그 검색 리사이클러뷰
        tagRV.layoutManager = tagSearchLayoutManager
    }

    private fun setRVAdapter() {
        //카테고리 리사이클러뷰  어댑터 연결
        materialsCategoryRV.adapter = categoryAdapter
        materialsCategoryRV.setHasFixedSize(true)

        //태그 검색 리사이클러뷰 어댑터 연결
        tagSearchAdapter = TagSearchAdapter(tagSearchItemListener)
        tagRV.adapter = tagSearchAdapter
    }

    override fun setRVScrollListener() {
        postLayoutManager = LinearLayoutManager(this)
        scrollListener =
            EndlessRecyclerViewScrollListener(postLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            EndlessRecyclerViewScrollListener.OnLoadMoreListener {
            override fun onLoadMore() {

                Log.d("TAG", "스크롤리스너 onLoadMore 실행!!")

                //loadMorePosts 에 null값을 추가해서 로딩뷰를 만든다.
                postAdapter.addLoadingView()
                loadMorePosts.add(null)

                //loadMorePosts 는 다음페이지 데이터를 받아올 때만 데이터를 추가하기 때문에 조건절로 비어있는지 확인해야함
                if (!loadMorePosts.isEmpty()) {
                    //loadMorePosts의 마지막 값이 null값이 있으면 무한스크롤 로딩 중이기 때문에 데이터를 받아오고, 로딩뷰를 제거한다.
                    if (loadMorePosts[loadMorePosts.size - 1] == null) {
                        presenter.loadMorePosts(presenter.requestPosts(presenter.getCategoryId()))
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

        if (loadMorePosts[loadMorePosts.size - 1] == null) {
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

    override fun removeLoadingView() {
        postAdapter.removeLoadingView()
    }

    override fun showCategories(categories: List<Category>) {
        categoryAdapter.setCategories(categories)
        categoryAdapter.notifyDataSetChanged()
        materialsCategoryRV.visibility = View.VISIBLE
    }

    override fun showPostDetailUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearchUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearSearchTagBar() {
        tagInputDeleteBtn.setOnClickListener { tagInput.text.clear() }
    }

    override fun deleteSearchTag() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initClickListeners() {
        setTitleBarButtons()
        setTapBarButtons()
        setTagSearchButtons()
        clearSearchTagBar()
    }

    override fun showSearchTags() {
        var inputTag: String = tagInput.text.toString()
        tagSearchWords.add(inputTag)

        //MutableList 의 값을 List 에 넣기 위해 String으로 변환해서 넣음
        var tagString: String = ""

        for (i in 0..tagSearchWords.size) {
            if (i == 0) {
                tagString = "$tagString" + "\"${tagSearchWords[i]}\""
            } else if (i == tagSearchWords.size - 1) {
                tagString = "$tagString" + ","+"\"${tagSearchWords[i]}\""
            } else if (i != 0 && i < tagSearchWords.size - 1 ) {
                tagString = "$tagString" + ","+"\"${tagSearchWords[i]}\""
            }
        }

        // 모델 형식에 맞춰서 List 타입으로 형변환
        val tagSearchWordsList: List<String> = listOf(tagString)
        Log.d(TAG, "777 tagSearchWordsList? : ${tagSearchWordsList}")

        presenter.requestAddSearchTag(
            presenter.getCategoryId(),
            "tag",
            tagSearchWordsList
        ) //검색 내용에 맞게 새로운 데이터를 가져오기 위한 요청값 프레젠터에 전달
        tagSearchAdapter.addTags(inputTag)
        tagSearchAdapter.notifyDataSetChanged()
        tagRV.setHasFixedSize(true)
        tagRV.visibility = View.VISIBLE
        tagInput.text.clear()
    }

    //태그 검색어 리스트에 추가하기
    fun setTagSearchWords() {

    }

    private fun setTagSearchButtons() {
        tagInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                showSearchTags()
                true
            } else {
                false
            }
        }
    }


    private fun setTitleBarButtons() {
        // 글작성, 글검색 버튼 클릭 리스너 달아주기
        writeBtn.setOnClickListener {
            val writeIntent = Intent(this, ActivityPostFirst::class.java)
            writeIntent.putExtra("CATEGORY_TYPE", "MATERIALS")
            startActivity(writeIntent)
        }
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
        private val itemListener: CategoryItemListener
    ) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

        lateinit var materialCategories: List<Category>

        var selectedPosition: Int = 0

        fun setCategories(list: List<Category>) {
            this.materialCategories = list
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_materials_category_rv, parent, false)
            return ViewHolder(inflatedView)
        }

        override fun getItemCount(): Int {
            return materialCategories.size - 1
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(materialCategories[position], position)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private var view: View = itemView

            fun bind(item: Category, position: Int) {

                view.apply {
                    if (selectedPosition == position) {
                        materialsCategoryBtn.apply {
                            text = item.name
                            setTextColor(ContextCompat.getColor(this.context, R.color.colorPurple))
                        }
                        categoryActiveBar.visibility = View.VISIBLE

                    } else {
                        materialsCategoryBtn.apply {
                            text = item.name
                            setTextColor(
                                ContextCompat.getColor(
                                    this.context,
                                    R.color.colorLightGrey
                                )
                            )
                        }
                        categoryActiveBar.visibility = View.INVISIBLE
                    }
                    setOnClickListener { itemListener.onCategoryClick(item.id, position) }
                }

            }
        }
    }

    private interface CategoryItemListener {
        fun onCategoryClick(categoryType: Int, position: Int)
    }

}
