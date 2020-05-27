package com.bnvs.metaler.view.manufactures

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.util.*
import com.bnvs.metaler.view.postfirst.ActivityPostFirst
import com.bnvs.metaler.view.search.ActivitySearch
import kotlinx.android.synthetic.main.activity_manufacture.*
import java.util.*

class ActivityManufactures : AppCompatActivity(),
    ContractManufactures.View {

    private val TAG = "ActivityManufactures"

    override lateinit var presenter: ContractManufactures.Presenter

    lateinit var posts: List<Post>
    var loadMorePosts: ArrayList<Post?> = ArrayList()

    lateinit var postAdapter: PostAdapter
    lateinit var scrollListener: EndlessRecyclerViewScrollListener
    lateinit var postLayoutManager: RecyclerView.LayoutManager

    lateinit var tagSearchAdapter: TagSearchAdapter
    private val tagSearchLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

    //MutableList 의 값을 PostsWithTagRequest 모델 타입인 List 에 맞추기 위해 String으로 변환해서 넣음
    var tagSearchWords: MutableList<String?> = mutableListOf()
    var tagString: String = ""

    /**
     * 가공 탭에서 보여지는 가공 게시물 리사이클러뷰 아이템에 달아줄 클릭리스너입니다
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

    //북마크 추가할 때, 리사이클러뷰 어댑터에 bookmark_id 를 보냄
    override fun postAdapterAddBookmark(position: Int, bookmarkId: Int) {
        postAdapter.apply {
            addBookmark(position, bookmarkId)
            notifyDataSetChanged()
        }
    }

    /**
     * 가공 탭의 태그 검색 리사이클러뷰 아이템에 달아줄 리스너입니다
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
                presenter.loadPosts(presenter.requestPosts())
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manufacture)

        // Create the presenter
        presenter = PresenterManufactures(this, this)

        // 가공 탭 presenter 시작
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
            presenter.loadPosts(presenter.requestPosts())
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

        //태그 검색 리사이클러뷰
        tagRV.layoutManager = tagSearchLayoutManager
    }

    private fun setRVAdapter() {
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
                //loadMorePosts 에 null값을 추가해서 로딩뷰를 만든다.
                postAdapter.addLoadingView()
                loadMorePosts.add(null)

                //loadMorePosts 는 다음페이지 데이터를 받아올 때만 데이터를 추가하기 때문에 조건절로 비어있는지 확인해야함
                if (!loadMorePosts.isEmpty() && tagSearchWords.isEmpty()) {
                    //loadMorePosts의 마지막 값이 null값이 있으면 무한스크롤 로딩 중이기 때문에 데이터를 받아오고, 로딩뷰를 제거한다.
                    if (loadMorePosts[loadMorePosts.size - 1] == null) {
                        presenter.loadMorePosts(presenter.requestPosts())
                    }
                } else if (!loadMorePosts.isEmpty() && !tagSearchWords.isEmpty()) {

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

    override fun showPostDetailUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearchUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearchTags() {
        var inputTag: String = tagInput.text.toString()
        tagSearchWords.add(inputTag)

        //초기화
        tagString = ""

        //MutableList 의 값을 List 에 넣기 위해 String(tagString)으로 변환해서 넣음
        for (i in 0..tagSearchWords.size) {
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

        //검색 내용에 맞게 새로운 데이터를 가져오기 위한 요청값 프레젠터에 전달
        tagSearchAdapter.addTags(inputTag)
        tagSearchAdapter.notifyDataSetChanged()
        tagRV.setHasFixedSize(true)
        tagRV.visibility = View.VISIBLE
        tagInput.text.clear()
    }

    override fun clearSearchTagBar() {
        tagInputDeleteBtn.setOnClickListener { tagInput.text.clear() }
    }

    private fun initClickListeners() {
        setTitleBarButtons()
        setTapBarButtons()
        setTagSearchButtons()
        clearSearchTagBar()
    }

    private fun setTagSearchButtons() {
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
            writeIntent.putExtra("CATEGORY_TYPE", "MANUFACTURES")
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
        materialsBtn.setOnClickListener {
            presenter.openMaterials(this, this)
            finishActivity()
        }
        manufactureBtn.setOnClickListener { }
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
}
