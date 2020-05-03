package com.bnvs.metaler.ui.bookmarks

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.bookmarks.model.Bookmark
import com.bnvs.metaler.util.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.activity_bookmark.*

class ActivityBookmarks : AppCompatActivity(), ContractBookmarks.View {

    private val TAG = "ActivityBookmarks"

    override lateinit var presenter: ContractBookmarks.Presenter

    lateinit var bookmarks: List<Bookmark>
    lateinit var bookmarkPostAdapter: BookmarkAdapter
    lateinit var scrollListener: EndlessRecyclerViewScrollListener
    lateinit var bookmarkPostLayoutManager: RecyclerView.LayoutManager
    var loadMorebookmarks: ArrayList<Bookmark?> = ArrayList()

    /**
     * 북마크 재료/가공 카테고리 눌렀을 때 보여지는 재료/가공 게시물 리사이클러뷰 아이템에 달아줄 클릭리스너입니다
     * 아이템 클릭 시, 클릭한 게시물의 post_id 를 presenter 에 전달합니다.
     * onPostClick -> 게시물을 클릭한 경우
     * onDeleteButtonClick -> 북마크 제거 버튼을 클릭한 경우
     * */
    private var bookmarkItemListener: BookmarkPostItemListener = object : BookmarkPostItemListener {
        override fun onPostClick(clickedPostId: Int) {
            presenter.openPostDetail(clickedPostId)
        }

        override fun onDeleteButtonClick(view: View, clickedPostId: Int, position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        // Create the presenter
        presenter = PresenterBookmarks(
            this@ActivityBookmarks,
            this@ActivityBookmarks
        )

        // Set up Buttons
        initClickListeners()

        activeMaterialsCategoryBtn()

        presenter.run {
            start()
        }

        setRVLayoutManager()

        setRVScrollListener()

    }

    override fun showPostDetailUi(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun setRVLayoutManager() {
        bookmarkPostLayoutManager = LinearLayoutManager(this)
        bookmarkRV.layoutManager = bookmarkPostLayoutManager
        bookmarkRV.setHasFixedSize(true)
    }

    private fun setRVScrollListener() {
        bookmarkPostLayoutManager = LinearLayoutManager(this)
        scrollListener =
            EndlessRecyclerViewScrollListener(bookmarkPostLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            EndlessRecyclerViewScrollListener.OnLoadMoreListener {
            override fun onLoadMore() {

                //loadMorePosts 에 null값을 추가해서 로딩뷰를 만든다.
                bookmarkPostAdapter.addLoadingView()
                loadMorebookmarks.add(null)

                //loadMorebookmarks 는 다음페이지 데이터를 받아올 때만 데이터를 추가하기 때문에 조건절로 비어있는지 확인해야함
                if (!loadMorebookmarks.isEmpty()) {
                    //loadMorebookmarks 마지막 값이 null값이 있으면 무한스크롤 로딩 중이기 때문에 데이터를 받아오고, 로딩뷰를 제거한다.
                    if (loadMorebookmarks[loadMorebookmarks.size - 1] == null) {
                        presenter.loadMoreBookmarkPosts(presenter.requestPosts(presenter.getCategoryType()))
                    }
                }

            }
        })
        bookmarkRV.addOnScrollListener(scrollListener)
    }

    override fun removeLoadingView() {
        bookmarkPostAdapter.removeLoadingView()
    }

    override fun showBookmarkPostsList(bookmarks: List<Bookmark>) {
        bookmarkPostAdapter = BookmarkAdapter(bookmarkItemListener)
        bookmarkPostAdapter.addPosts(bookmarks)
        bookmarkPostAdapter.notifyDataSetChanged()
        bookmarkRV.adapter = bookmarkPostAdapter
        bookmarkRV.visibility = View.VISIBLE
    }

    override fun showMoreBookmarkPostsList(bookmarks: List<Bookmark>) {

        if (loadMorebookmarks[loadMorebookmarks.size - 1] == null) {
            Handler().postDelayed({
                bookmarkPostAdapter.removeLoadingView()

                loadMorebookmarks.removeAll(loadMorebookmarks)
                loadMorebookmarks.addAll(bookmarks)

                bookmarkPostAdapter.addMorePosts(loadMorebookmarks)
                scrollListener.setLoaded()

                bookmarkRV.post {
                    bookmarkPostAdapter.notifyDataSetChanged()
                }

            }, 1000)
        }
    }

    override fun showBookmarkDeleteDialog(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initClickListeners() {
        setCategoryButtons()
        setTapBarButtons()
    }

    private fun activeMaterialsCategoryBtn() {
        materialsCategoryBtn.setTextColor(ContextCompat.getColor(this, R.color.colorPurple))
        materialsBar.visibility = View.VISIBLE
        manufactureCategoryBtn.setTextColor(ContextCompat.getColor(this, R.color.colorLightGrey))
        manufactureBar.visibility = View.INVISIBLE
    }

    private fun activeManufactureCategoryBtn() {
        manufactureCategoryBtn.setTextColor(ContextCompat.getColor(this, R.color.colorPurple))
        manufactureBar.visibility = View.VISIBLE
        materialsCategoryBtn.setTextColor(ContextCompat.getColor(this, R.color.colorLightGrey))
        materialsBar.visibility = View.INVISIBLE
    }

    private fun setCategoryButtons() {
        materialsCategoryBtn.setOnClickListener {
            activeMaterialsCategoryBtn()
            bookmarkPostAdapter.resetList()
            presenter.openMaterialsList()
        }
        manufactureCategoryBtn.setOnClickListener {
            activeManufactureCategoryBtn()
            bookmarkPostAdapter.resetList()
            presenter.openManufacturesList()
        }
    }

    private fun setTapBarButtons() {
        homeBtn.setOnClickListener { presenter.openHome(this, this) }
        materialsBtn.setOnClickListener { presenter.openMaterials(this, this) }
        manufactureBtn.setOnClickListener { presenter.openManufactures(this, this) }
        bookmarkBtn.setOnClickListener { presenter.openBookmarks(this, this) }
        myPageBtn.setOnClickListener { presenter.openMyPage(this, this) }
    }


}
