package com.bnvs.metaler.ui.bookmarks

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.bookmarks.model.Bookmark
import com.bnvs.metaler.util.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.activity_bookmark.*

class ActivityBookmarks : AppCompatActivity(), ContractBookmarks.View {

    private val TAG = "ActivityBookmarks"

    override lateinit var presenter: ContractBookmarks.Presenter

    lateinit var bookmarks: List<Bookmark>
    lateinit var bookmarkAdapter: BookmarkAdapter
    lateinit var scrollListener: EndlessRecyclerViewScrollListener
    lateinit var bookmarkLayoutManager: RecyclerView.LayoutManager
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
    /*private val bookmarkPostAdapter = BookmarkPostAdapter(ArrayList(0), bookmarkItemListener)
    private val bookmarkPostLayoutManager = LinearLayoutManager(this)*/


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

        // Set up posts recyclerView
        /*bookmarkRV.apply {
            adapter = bookmarkPostAdapter
            layoutManager = bookmarkPostLayoutManager
        }*/


        presenter.run {
            start()
        }


    }

    override fun showPostDetailUi(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showBookmarkPostsList(bookmarks: List<Bookmark>) {
        /*bookmarkPostAdapter.setPosts(posts)*/
    }

    override fun showBookmarkDeleteDialog(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initClickListeners() {
        setCategoryButtons()
        setTapBarButtons()
    }

    private fun setCategoryButtons() {
        materialsCategoryBtn.setOnClickListener { presenter.openMaterialsList() }
        manufactureCategoryBtn.setOnClickListener { presenter.openManufacturesList() }
    }

    private fun setTapBarButtons() {
        homeBtn.setOnClickListener { presenter.openHome(this, this) }
        materialsBtn.setOnClickListener { presenter.openMaterials(this, this) }
        manufactureBtn.setOnClickListener { presenter.openManufactures(this, this) }
        bookmarkBtn.setOnClickListener { presenter.openBookmarks(this, this) }
        myPageBtn.setOnClickListener { presenter.openMyPage(this, this) }
    }


    private interface BookmarkPostItemListener {
        fun onPostClick(clickedPostId: Int)
        fun onDeleteButtonClick(view: View, clickedPostId: Int, position: Int)

    }

}
