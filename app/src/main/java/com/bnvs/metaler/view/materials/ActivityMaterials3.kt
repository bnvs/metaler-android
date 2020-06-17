package com.bnvs.metaler.view.materials

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.util.*
import com.bnvs.metaler.view.postfirst.ActivityPostFirst
import com.bnvs.metaler.view.search.ActivitySearch
import kotlinx.android.synthetic.main.activity_materials.*
import kotlinx.android.synthetic.main.item_materials_category_rv.view.*

class ActivityMaterials3 : AppCompatActivity(),
    ContractMaterials.View {

    private val TAG = "ActivityMaterials"

    override lateinit var presenter: ContractMaterials.Presenter

    //재료 게시글 보여주는 리사이클러뷰에서 사용하는 어댑터, 스크롤리스너, 레이아웃매니저,
    lateinit var postAdapter: PostAdapter
    lateinit var scrollListener: EndlessRecyclerViewScrollListener
    lateinit var postLayoutManager: RecyclerView.LayoutManager

    //스크롤 내리면서 추가로 받아오는 게시글 데이터를 담는 변수
    var loadMorePosts: ArrayList<Post?> = ArrayList()

    //MutableList 의 값을 PostsWithTagRequest 모델 타입인 List 에 맞추기 위해 String으로 변환해서 넣음
    var tagSearchWords: MutableList<String?> = mutableListOf()
    var tagString: String = ""

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
            tagSearchAdapter.notifyDataSetChanged()

            tagSearchWords.removeAt(position)

            //초기화
            tagString = ""

            if (tagSearchWords.size > 0) {
                //MutableList 의 값을 List 에 넣기 위해 String(tagString) 으로 변환해서 넣음
                for (i in 0 until tagSearchWords.size) {
                    if (i == 0) {
                        tagString = "$tagString" + "\"${tagSearchWords[i]}\""
                    } else if (i != 0 && i <= tagSearchWords.size - 1) {
                        tagString = "$tagString" + "," + "\"${tagSearchWords[i]}\""
                    }
                }
                // 모델 형식에 맞춰서 List 타입으로 형변환
                val tagSearchWordsList: List<String> = listOf(tagString)

                presenter.loadSearchTagPosts(
                    presenter.requestAddSearchTag(
                        presenter.getCategoryId(),
                        "tag",
                        tagSearchWordsList
                    )
                )
            }

            if (tagSearchWords.size == 0) {
                tagRV.visibility = View.GONE
                presenter.loadPosts(presenter.requestPosts(presenter.getCategoryId()))
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
            bookmarkId: Int,
            position: Int
        ) {
            if (bookmarkId == 0) {
                presenter.addBookmark(clickedPostId, bookmarkId, position)
            } else if (bookmarkId > 0) {
                presenter.deleteBookmark(bookmarkId)
                postAdapter.apply {
                    deleteBookmark(position)
                    notifyDataSetChanged()
                }
            }
        }

    }

    override fun postAdapterAddBookmark(position: Int, bookmarkId: Int) {
        postAdapter.apply {
            addBookmark(position, bookmarkId)
            notifyDataSetChanged()
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
            refreshLayout.isRefreshing = false
            scrollListener.setLoaded()
        }
    }

    override fun showRefreshPosts(posts: List<Post>) {
        postAdapter.resetList()
        postAdapter.addPosts(posts)
        postAdapter.notifyDataSetChanged()
    }

    override fun showError404() {
        postAdapter.resetList()
        postAdapter.notifyDataSetChanged()
        postsRV.visibility = View.INVISIBLE
        error404Group.visibility = View.VISIBLE
    }

    override fun hideError404() {
        postsRV.visibility = View.VISIBLE
        error404Group.visibility = View.INVISIBLE
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
        //게시글 리사이클러뷰 어댑터 연결의 경우는 showPosts()함수에서 따로 연결함

        //카테고리 리사이클러뷰  어댑터 연결
        materialsCategoryRV.adapter = categoryAdapter
        materialsCategoryRV.setHasFixedSize(true)

        //태그 검색 리사이클러뷰 어댑터 연결
        tagSearchAdapter = TagSearchAdapter(tagSearchItemListener)
        tagRV.adapter = tagSearchAdapter
    }

    //게시글 리사이클러뷰에서만 쓰는 스크롤 리스너
    override fun setRVScrollListener() {
        postLayoutManager = LinearLayoutManager(this)
        scrollListener =
            EndlessRecyclerViewScrollListener(postLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            EndlessRecyclerViewScrollListener.OnLoadMoreListener {
            override fun onLoadMore() {

                //loadMorePosts 에 null값을 추가해서 로딩뷰를 만든다.
                postAdapter.addLoadingView()
                loadMorePosts.add(null)

                //loadMorePosts 는 다음페이지 데이터를 받아올 때만 데이터를 추가하기 때문에 조건절로 비어있는지 확인해야함
                if (!loadMorePosts.isEmpty() && tagSearchWords.isEmpty()) {//태그 검색어가 없는 경우
                    //loadMorePosts의 마지막 값이 null값이 있으면 무한스크롤 로딩 중이기 때문에 데이터를 받아오고, 로딩뷰를 제거한다.
                    if (loadMorePosts[loadMorePosts.size - 1] == null) {
                        presenter.loadMorePosts(presenter.requestPosts(presenter.getCategoryId()))
                    }
                } else if (!loadMorePosts.isEmpty() && !tagSearchWords.isEmpty()) {//태그 검색어가 있는 경우

                    //사용자가 추가한 태그 검색어를 담는 리스트
                    //TODO : 계속 생성하는 방식 말고 다르게 수정하기
                    val tagSearchWordsList: List<String> = listOf(tagString)

                    if (loadMorePosts[loadMorePosts.size - 1] == null) {
                        presenter.loadMoreSearchTagPosts(
                            presenter.requestAddSearchTag(
                                presenter.getCategoryId(),
                                "tag",
                                tagSearchWordsList
                            )
                        )
                    }
                }

            }
        })
        postsRV.addOnScrollListener(scrollListener)
    }


    //처음에 재료탭 들어왔을 때 데이터 가져오는 함수
    override fun showPosts(posts: List<Post>) {
        postAdapter = PostAdapter(itemListener)
        postAdapter.addPosts(posts)
        postAdapter.notifyDataSetChanged()
        postsRV.adapter = postAdapter
    }

    override fun showMorePosts(posts: List<Post>) {
        //TODO : loadMorePosts를 굳이 전역변수 둘 필요없이 posts 매개변수를 사용하면 될 듯
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

    //로딩 제거
    override fun removeLoadingView() {
        postAdapter.removeLoadingView()
    }

    //재료탭 카테고리 불러옴
    override fun showCategories(categories: List<Category>) {
        categoryAdapter.setCategories(categories)
        categoryAdapter.notifyDataSetChanged()
        materialsCategoryRV.visibility = View.VISIBLE
    }

    private fun initClickListeners() {
        setTitleBarButtons()
        setTapBarButtons()
        setTagSearchButtons()
        clearSearchTagBar()
    }

    override fun showSearchTags() {
        //TODO : 프레젠터로 옮겨야할 듯
        tagString = ""
        var inputTag: String = tagInput.text.toString()

        //공백으로 검색 시 예외처리
        if (inputTag.isNullOrBlank()) {
            Toast.makeText(
                this,
                "검색어를 다시 입력해주세요.",
                Toast.LENGTH_SHORT
            ).show()
            tagInput.text.clear()
            return
        }

        tagSearchWords.add(inputTag)

        //MutableList 의 값을 List 에 넣기 위해 String(tagString) 으로 변환해서 넣음
        for (i in 0..tagSearchWords.size) {
            if (i == 0) {
                //서버 통신할 때 검색어 스트링에 따옴표 추가해주는 부분
                tagString = "\"${tagSearchWords[i]}\""
            } else if (i != 0 && i <= tagSearchWords.size - 1) {
                //쉼표 추가하려고 사용한 부분
                //TODO : List를 toSting 으로 변환하기
                tagString = "$tagString" + "," + "\"${tagSearchWords[i]}\""
            }
        }

        // 모델 형식에 맞춰서 List 타입으로 형변환
        val tagSearchWordsList: List<String> = listOf(tagString)

        //검색 내용에 맞게 새로운 데이터를 가져오기 위한 요청값 프레젠터에 전달
        presenter.loadSearchTagPosts(
            presenter.requestAddSearchTag(
                presenter.getCategoryId(),
                "tag",
                tagSearchWordsList
            )
        )


        tagSearchAdapter.addTags(inputTag)
        tagSearchAdapter.notifyDataSetChanged()
        tagRV.setHasFixedSize(true)
        tagRV.visibility = View.VISIBLE
        //입력창에 적힌 단어 지움
        tagInput.text.clear()
    }

    //태그 검색 입력창에 쓰고있던 것만 지워짐
    override fun clearSearchTagBar() {
        tagInputDeleteBtn.setOnClickListener { tagInput.text.clear() }
    }


    //TODO : View 를 SearchView 로 변경하기
    override fun setTagSearchButtons() {
        tagInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.resetPageNum()
                showSearchTags()
                true
            } else {
                false
            }
        }
    }


    private fun setTitleBarButtons() {
        writeBtn.setOnClickListener {
            val writeIntent = Intent(this, ActivityPostFirst::class.java)
            writeIntent.putExtra("CATEGORY_TYPE", "MATERIALS")
            startActivity(writeIntent)
        }

        searchBtn.setOnClickListener {
            val searchIntent = Intent(this, ActivitySearch::class.java)
            searchIntent.putExtra("CATEGORY_TYPE", "MATERIALS")
            startActivity(searchIntent)
        }
    }

    private fun setTapBarButtons() {
        homeBtn.setOnClickListener {
            presenter.openHome(this, this)
            finishActivity()
        }
        materialsBtn.setOnClickListener { }
        manufactureBtn.setOnClickListener {
            presenter.openManufactures(this, this)
            finishActivity()
        }
        bookmarkBtn.setOnClickListener {
            presenter.openBookmarks(this, this)
            finishActivity()
        }
        myPageBtn.setOnClickListener {
            presenter.openMyPage(this, this)
            finishActivity()
        }
    }

    private fun finishActivity() {
        finish()
        overridePendingTransition(0, 0)
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