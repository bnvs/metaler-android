package com.bnvs.metaler.view.search

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.util.EndlessRecyclerViewScrollListener
import com.bnvs.metaler.util.PostAdapter
import com.bnvs.metaler.util.PostItemListener
import kotlinx.android.synthetic.main.activity_search.*

class ActivitySearch : AppCompatActivity(), ContractSearch.View {

    companion object {
        private const val TAG = "ActivitySearch"
        private const val MATERIALS = 1
        private const val MANUFACTURE = 10
    }

    override lateinit var presenter: ContractSearch.Presenter

    lateinit var posts: List<Post>
    lateinit var postAdapter: PostAdapter
    lateinit var scrollListener: EndlessRecyclerViewScrollListener
    lateinit var postLayoutManager: RecyclerView.LayoutManager
    var loadMorePosts: ArrayList<Post?> = ArrayList()

    //검색어
    var searchWord: String = ""

    var categoryId: Int = 0

    /**
     * 검색 탭에서 보여지는 재료 게시물 리사이클러뷰 아이템에 달아줄 리스너입니다
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val getCategoryType = intent.getStringExtra("CATEGORY_TYPE")
        if (getCategoryType == "MATERIALS") {
            categoryId = MATERIALS
        } else if (getCategoryType == "MANUFACTURE") {
            categoryId = MANUFACTURE
        }


        presenter = PresenterSearch(
            categoryId,
            this@ActivitySearch,
            this@ActivitySearch
        )

        initClickListeners()

        setRVScrollListener()

    }

    override fun inputSearchWord() {
        searchWord = searchInput.text.toString()

        presenter.loadSearchPosts(
            presenter.requestSearchPosts(
                presenter.getCategoryId(),
                searchWord
            )
        )

        searchInput.text.clear()
    }

    override fun showSearchTotalNum(totalNum: Int) {
        searchResult.visibility = View.VISIBLE
        searchResultNum.text = "$totalNum"
    }

    override fun showSearchPosts(posts: List<Post>) {
        postLayoutManager = LinearLayoutManager(this)
        postsRV.layoutManager = postLayoutManager
        postsRV.setHasFixedSize(true)

        postAdapter = PostAdapter(itemListener)
        postAdapter.addPosts(posts)
        postAdapter.notifyDataSetChanged()
        postsRV.adapter = postAdapter
        postsRV.visibility = View.VISIBLE
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
                if (!loadMorePosts.isEmpty()) {
                    //loadMorePosts의 마지막 값이 null값이 있으면 무한스크롤 로딩 중이기 때문에 데이터를 받아오고, 로딩뷰를 제거한다.
                    if (loadMorePosts[loadMorePosts.size - 1] == null) {
                        presenter.loadMoreSearchPosts(presenter.requestSearchPosts(presenter.getCategoryId(),searchWord))
                    }
                } else if (!loadMorePosts.isEmpty()) {
                    if (loadMorePosts[loadMorePosts.size - 1] == null) {
                        presenter.loadMoreSearchPosts(
                            presenter.requestSearchPosts(
                                presenter.getCategoryId(),
                                searchWord
                            )
                        )
                    }
                }

            }
        })
        postsRV.addOnScrollListener(scrollListener)
    }

    override fun showMoreSearchPosts(posts: List<Post>) {
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

    private fun initClickListeners() {
        setTitleBarButtons()
        setSearchButtons()
        searchInputDeleteBtn.setOnClickListener { searchInput.text.clear() }
    }

    override fun setSearchButtons() {
        searchInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.resetPageNum()
                inputSearchWord()
                true
            } else {
                false
            }

        }
    }

    private fun setTitleBarButtons() {
        backBtn.setOnClickListener {
            finish()
        }
    }

    override fun onRefresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showRefreshPosts(posts: List<Post>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideError404() {
        postsRV.visibility = View.VISIBLE
        error404Group.visibility = View.INVISIBLE
    }

    override fun showError404() {
        postAdapter.resetList()
        postAdapter.notifyDataSetChanged()
        postsRV.visibility = View.INVISIBLE
        error404Group.visibility = View.VISIBLE
    }

}
