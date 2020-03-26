package com.example.metaler_android.bookmarks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.metaler_android.R
import com.example.metaler_android.data.post.Post
import com.example.metaler_android.home.PresenterHome
import com.example.metaler_android.util.PostAdapter
import kotlinx.android.synthetic.main.activity_bookmark.*
import kotlinx.android.synthetic.main.activity_bookmark.bookmarkBtn
import kotlinx.android.synthetic.main.activity_bookmark.homeBtn
import kotlinx.android.synthetic.main.activity_bookmark.manufactureBtn
import kotlinx.android.synthetic.main.activity_bookmark.materialsBtn
import kotlinx.android.synthetic.main.activity_bookmark.myPageBtn
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_bookmark_rv.view.*

class ActivityBookmarks : AppCompatActivity(), ContractBookmarks.View {

    private val TAG = "ActivityBookmarks"

    override lateinit var presenter: ContractBookmarks.Presenter


    /**
     * 북마크 재료/가공 카테고리 눌렀을 때 보여지는 재료/가공 게시물 리사이클러뷰 아이템에 달아줄 클릭리스너입니다
     * 아이템 클릭 시, 클릭한 게시물의 post_id 를 presenter 에 전달합니다.
     * */
    private var bookmarkItemListener: BookmarkPostItemListener = object : BookmarkPostItemListener {
        override fun onPostClick(clickedPostId: Int) {
            presenter.openPostDetail(clickedPostId)
        }

        override fun onMoreButtonClick(view: View, clickedPostId: Int, position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
    private val bookmarkPostAdapter = BookmarkPostAdapter(ArrayList(0), bookmarkItemListener)
    private val bookmarkPostLayoutManager = LinearLayoutManager(this)






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        // Create the presenter
        presenter = PresenterBookmarks(
            this@ActivityBookmarks,
            this@ActivityBookmarks
        )

        initClickListeners()

        presenter.run {
            start()
        }

    }

    override fun showPostDetailUi(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMaterialsList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showManufacturesList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showBookmarkDeleteDialog(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initClickListeners() {
        setCategoryButtons()
        setTapBarButtons()
    }

    private fun setCategoryButtons(){
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



    //북마크 리사이클러뷰 아이템 레이아웃(북마크 버튼 대신 delete 버튼있음)이 PostAdapter에서 쓰는 레이아웃이랑 달라서 일부 바꿈
    private class BookmarkPostAdapter(
        private var posts: List<Post>,
        private val itemListener: BookmarkPostItemListener
    ) : RecyclerView.Adapter<BookmarkPostAdapter.ViewHolder>() {

        fun setPosts(list: List<Post>) {
            this.posts = list
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bookmark_rv, parent, false)
            return ViewHolder(inflatedView)
        }

        override fun getItemCount(): Int {
            return posts.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(posts[position], position)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private var view: View = itemView

            fun bind(item: Post, position: Int) {

                var tags = ""
                for (tag in item.tags) {
                    tags += "#$tag "
                }

                view.apply {
                    title.text = item.title
                    userName.text = item.nickname
                    date.text = item.date
//                    tag.text = item.tags
                    dislikeNum.text = item.dis_like.toString()
                    likeNum.text = item.like.toString()
                    setOnClickListener { itemListener.onPostClick(item.post_id) }
                    moreBtn.setOnClickListener {
                        itemListener.onMoreButtonClick(view, item.post_id,position)
                    }

                }

                Glide.with(view)
                    .load(item.attach_url)
                    .into(view.img)
            }
        }
    }


    private interface BookmarkPostItemListener {
        fun onPostClick(clickedPostId: Int)
        fun onMoreButtonClick(view: View, clickedPostId: Int, position: Int)

    }

}
