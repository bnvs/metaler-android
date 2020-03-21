package com.example.metaler_android.manufactures

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metaler_android.R
import com.example.metaler_android.data.post.Post
import com.example.metaler_android.home.ActivityHome
import com.example.metaler_android.materials.ActivityMaterials
import com.example.metaler_android.util.PostAdapter
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

        // Set up posts recyclerView
        postsRV.apply {
            adapter = postAdapter
            layoutManager = postLayoutManager
        }

        // 홈 탭에서 보여줄 데이터 가져오기 시작
        // 탭 바 아이콘에 클릭 리스너 달아줌
        presenter.run {
            start()
            setTapBar(this@ActivityManufactures)
        }

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

    // 하단 탭 바에 리스너를 추가한다
    override fun setTapBarListener(context: Context) {
        homeIcon.setOnClickListener {
            Intent(context, ActivityHome::class.java).also {
                startActivity(it)
            }
        }

        materialsIcon.setOnClickListener {
            Intent(context, ActivityMaterials::class.java).also {
                startActivity(it)
            }
        }

        manufactureIcon.setOnClickListener {
            Intent(context, ActivityManufactures::class.java).also {
                startActivity(it)
            }
        }

        /*bookmarkIcon.setOnClickListener {
            Intent(context, ActivityBookmarks::class.java).also {
                addFlags(it)
                startActivity(it)
            }
        }

        myPageIcon.setOnClickListener {
            Intent(context, ActivityMyPage::class.java).also {
                addFlags(it)
                startActivity(it)
            }
        }*/
    }

    interface PostItemListener {
        fun onPostClick(clickedPostId: Int)

        fun onBookmarkButtonClick( view: View, clickedPostId: Int, isBookmark: Boolean, position: Int)
    }
}
