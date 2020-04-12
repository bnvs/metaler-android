package com.bnvs.metaler.manufactures

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.posts.Post
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_posts_rv.view.*

class ManufacturesPostAdapter(
    private var posts: ArrayList<Post?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var mcontext: Context

//    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setPosts(list: ArrayList<Post?>) {
        this.posts.addAll(list)
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): String? {
        return posts[position].toString()
    }

    fun addLoadingView() {
        //add loading item
        Handler().post {
            posts.add(null)
            notifyItemInserted(posts.size - 1)
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        if (posts.size != 0) {
            posts.removeAt(posts.size - 1)
            notifyItemRemoved(posts.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mcontext = parent.context
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_posts_rv, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(mcontext).inflate(R.layout.item_loading, parent, false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                view.progressBar.indeterminateDrawable.colorFilter = BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
            } else {
                view.progressBar.indeterminateDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
            }
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (posts[position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
//            holder.itemView.itemtextview.text = posts[position]
            holder.itemView.title.text = posts[position]!!.title
//            holder.itemView.apply {
//                title.text = posts[position].title
//                userName.text = item.profile_nickname
//                date.text = item.created_at
//                tags.text = tagString
//                dislikeNum.text = item.hate_count.toString()
//                likeNum.text = item.good_count.toString()
//                setOnClickListener { itemListener.onPostClick(item.id) }
//                bookmarkBtn.setOnClickListener {
//                    itemListener.onBookmarkButtonClick(view, item.id, item.is_bookmark, position)
//                }
//                if (item.is_bookmark) {
//                    bookmarkBtn.setImageResource(R.drawable.ic_list_bookmark_active_x3)
//                }
//            }
//            holder.bind(posts[position], position)
        }
    }
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView

        fun bind(item: Post, position: Int) {

            var tagString = ""
            for (tag in item.tags) {
                tagString += "#$tag "
            }

            view.apply {
                title.text = item.title
                userName.text = item.profile_nickname
                date.text = item.created_at
                tags.text = tagString
                dislikeNum.text = item.hate_count.toString()
                likeNum.text = item.good_count.toString()
//                setOnClickListener { itemListener.onPostClick(item.id) }
//                bookmarkBtn.setOnClickListener {
//                    itemListener.onBookmarkButtonClick(view, item.id, item.is_bookmark, position)
//                }
                if (item.is_bookmark) {
                    bookmarkBtn.setImageResource(R.drawable.ic_list_bookmark_active_x3)
                }
            }

//            Glide.with(view)
//                .load(item.attach_url)
//                .into(view.img)
        }
    }

    interface PostItemListener{
        fun onPostClick(clickedPostId: Int)

        fun onBookmarkButtonClick(view: View, clickedPostId: Int, isBookmark: Boolean, position: Int)
    }

    object Constant {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

}