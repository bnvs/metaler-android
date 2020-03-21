package com.example.metaler_android.manufactures

import android.content.Context
import android.content.Intent
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
import com.example.metaler_android.home.ActivityHome
import com.example.metaler_android.materials.ActivityMaterials
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

        override fun onBookmarkButtonClick(clickedPostId: Int, view: View, post: Post) {
            if (!post.is_bookmark) {
                view.bookmarkBtn.setImageResource(R.drawable.ic_list_bookmark_active_x3)
                presenter.addBookmark()
            }else {
                view.bookmarkBtn.setImageResource(R.drawable.ic_list_bookmark_inactive_x3)
                presenter.deleteBookmark()
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

    }

    override fun showPosts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    /**
     * 가공 탭의 리사이클러뷰에 사용할 어댑터입니다.
     * 재료 탭의 리사이클러뷰에 재사용 가능하게 작성할 예정입니다
     * */
    private class PostAdapter(
        private var posts: List<Post>,
        private val itemListener: PostItemListener
    ) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

        fun setPosts(list: List<Post>) {
            this.posts = list
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_posts_rv, parent, false)
            return ViewHolder(inflatedView)
        }

        override fun getItemCount(): Int {
            return posts.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(posts[position])
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private var view: View = itemView

            fun bind(item: Post) {

                var tags = ""
                for (tag in item.tags) {
                    tags += "#$tag "
                }

                view.apply {
                    title.text = item.title
                    userName.text = item.nickname
                    date.text = item.date
                    // tagRV.text = tags TODO : 태그 보여주는 방식 논의 필요
                    dislikeNum.text = item.dis_like.toString()
                    likeNum.text = item.like.toString()
                    setOnClickListener { itemListener.onPostClick(item.post_id) }
                    bookmarkBtn.setOnClickListener { itemListener.onBookmarkButtonClick(item.post_id, view, item) }
                    if (item.is_bookmark) {
                        bookmarkBtn.setImageResource(R.drawable.ic_list_bookmark_active_x3)
                    }
                }

                Glide.with(view)
                    .load(item.attach_url)
                    .into(view.img)
            }
        }
    }

    private interface PostItemListener {
        fun onPostClick(clickedPostId: Int)

        fun onBookmarkButtonClick(clickedPostId: Int, view: View, post: Post)
    }
}
