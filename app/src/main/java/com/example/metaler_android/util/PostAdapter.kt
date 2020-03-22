package com.example.metaler_android.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.metaler_android.R
import com.example.metaler_android.data.post.Post
import com.example.metaler_android.manufactures.ActivityManufactures
import kotlinx.android.synthetic.main.item_posts_rv.view.*

/**
 * 가공 탭의 리사이클러뷰에 사용할 어댑터입니다.
 * 재료 탭의 리사이클러뷰에 재사용 가능하게 작성할 예정입니다
 * */
class PostAdapter(
    private var posts: List<Post>,
    private val itemListener: ActivityManufactures.PostItemListener
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    fun setPosts(list: List<Post>) {
        this.posts = list
    }

    fun setBookmark(position: Int) {
        posts[position].is_bookmark = !posts[position].is_bookmark
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
                // tagRV.text = tags TODO : 태그 보여주는 방식 논의 필요
                dislikeNum.text = item.dis_like.toString()
                likeNum.text = item.like.toString()
                setOnClickListener { itemListener.onPostClick(item.post_id) }
                bookmarkBtn.setOnClickListener {
                    itemListener.onBookmarkButtonClick(view, item.post_id, item.is_bookmark, position)
                }
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