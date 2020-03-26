package com.bnvs.metaler.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bnvs.metaler.R
import com.bnvs.metaler.data.post.Post
import kotlinx.android.synthetic.main.item_posts_rv.view.*

/**
 * 재료/가공 탭의 게시물 리사이클러뷰에 사용하는 어댑터입니다.
 * */
class PostAdapter(
    private var posts: List<Post>,
    private val itemListener: PostItemListener
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

            var tagString = ""
            for (tag in item.tags) {
                tagString += "#$tag "
            }

            view.apply {
                title.text = item.title
                userName.text = item.nickname
                date.text = item.date
                tags.text = tagString
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