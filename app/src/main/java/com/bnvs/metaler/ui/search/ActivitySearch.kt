package com.bnvs.metaler.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.ui.myposts.ContractMyPosts
import com.bnvs.metaler.util.EndlessRecyclerViewScrollListener
import com.bnvs.metaler.util.PostAdapter
import com.bnvs.metaler.util.PostItemListener

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
            isBookmark: Int,
            position: Int
        ) {

            if (isBookmark == 0) {
                presenter.addBookmark(clickedPostId)
                postAdapter.apply {
                    setBookmark(position)
                    notifyDataSetChanged()
                }
            } else if (isBookmark == 1) {
                presenter.deleteBookmark(clickedPostId)
                postAdapter.apply {
                    setBookmark(position)
                    notifyDataSetChanged()
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val getCategoryType = intent.getStringExtra("CATEGORY_TYPE")
        if (getCategoryType == "MATERIALS"){
            categoryId = MATERIALS
        }else if (getCategoryType == "MANUFACTURE"){
            categoryId = MANUFACTURE
        }


        presenter = PresenterSearch(
            categoryId,
            this@ActivitySearch,
            this@ActivitySearch
        )
    }

    override fun showSearchPosts(posts: List<Post>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMoreSearchPosts(posts: List<Post>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeLoadingView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setRVScrollListener() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRefresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showRefreshPosts(posts: List<Post>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideError404() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError404() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
