package com.example.metaler_android.manufactures

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metaler_android.R
import com.example.metaler_android.data.post.Post
import com.example.metaler_android.util.PostAdapter
import com.example.metaler_android.util.PostItemListener
import kotlinx.android.synthetic.main.activity_manufacture.*
import kotlinx.android.synthetic.main.item_posts_rv.view.*

class ActivityManufactures : AppCompatActivity(), ContractManufactures.View {

    private val TAG = "ActivityManufactures"

    override lateinit var presenter: ContractManufactures.Presenter

    /**
     * 가공 탭에서 보여지는 가공 게시물 리사이클러뷰 아이템에 달아줄 클릭리스너입니다
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manufacture)

        // Create the presenter
        presenter = PresenterManufactures(
            this@ActivityManufactures,
            this@ActivityManufactures
        )

        // Set up Buttons
        initClickListeners()

        // Set up posts recyclerView
        postsRV.apply {
            adapter = postAdapter
            layoutManager = postLayoutManager
        }

        // 가공 탭 presenter 시작
        presenter.run {
            start()
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun showPosts(posts: List<Post>) {
        postAdapter.setPosts(posts)
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
}
