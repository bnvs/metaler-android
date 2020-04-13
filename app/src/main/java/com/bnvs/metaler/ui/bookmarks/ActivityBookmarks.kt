package com.bnvs.metaler.ui.bookmarks

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bnvs.metaler.R
import com.bnvs.metaler.data.posts.Post
import kotlinx.android.synthetic.main.activity_bookmark.*

class ActivityBookmarks : AppCompatActivity(), ContractBookmarks.View {

    private val TAG = "ActivityBookmarks"

    override lateinit var presenter: ContractBookmarks.Presenter


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

    override fun showMaterialsList(posts: List<Post>) {
        /*bookmarkPostAdapter.setPosts(posts)*/
    }

    override fun showManufacturesList(posts: List<Post>) {
        /*bookmarkPostAdapter.setPosts(posts)*/
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
    /*private class BookmarkPostAdapter(
        private var posts: List<Post>,
        private val itemListener: BookmarkPostItemListener
    ) : RecyclerView.Adapter<BookmarkPostAdapter.ViewHolder>() {

        private val VIEW_TYPE_ITEM = 0
        private val VIEW_TYPE_LOADING = 1


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
                    deleteBtn.setOnClickListener {
                        itemListener.onDeleteButtonClick(view, item.post_id,position)
                    }

                }

                Glide.with(view)
                    .load(item.attach_url)
                    .into(view.img)
            }
        }
    }*/


    private interface BookmarkPostItemListener {
        fun onPostClick(clickedPostId: Int)
        fun onDeleteButtonClick(view: View, clickedPostId: Int, position: Int)

    }

}
