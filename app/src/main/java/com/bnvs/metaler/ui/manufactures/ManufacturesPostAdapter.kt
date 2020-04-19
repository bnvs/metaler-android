package com.bnvs.metaler.ui.manufactures

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.posts.model.Post
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_posts_rv.view.*

class ManufacturesPostAdapter(
    private var posts: List<Post>,
    private var itemListener: ManufacturesPostItemListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context

    var arrayList = ArrayList<Post?>()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addPosts(list: ArrayList<Post?>) {
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

    fun setBookmark(position: Int) {
        posts[position].is_bookmark = !posts[position].is_bookmark
    }

    fun getItemAtPosition(position: Int): String? {
        return posts[position].toString()
    }

    fun addLoadingView() {
        //add loading item
        Handler().post {
            arrayList.add(null)
            Log.d("어댑터", "-----arrayList에 null값 추가! arrayLists: ${arrayList}")
            Log.d("어댑터", "----- posts: ${posts}")
            notifyItemInserted(arrayList.size - 1)
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        if (arrayList.size != 0) {
            arrayList.removeAt(arrayList.size - 1)
            notifyItemRemoved(arrayList.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_posts_rv, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                view.progressBar.indeterminateDrawable.colorFilter =
                    BlendModeColorFilter(R.color.colorLightPurple, BlendMode.SRC_ATOP)
            } else {
                view.progressBar.indeterminateDrawable.setColorFilter(
                    Color.GRAY,
                    PorterDuff.Mode.MULTIPLY
                )
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
            var tagString = ""
            for (tag in posts[position].tags) {
                tagString += "#$tag "
            }


            holder.itemView.apply {
                title.text = posts[position].title
                userName.text = posts[position].profile_nickname
                date.text = posts[position].date
                tags.text = tagString
                dislikeNum.text = posts[position].disliked.toString()
                likeNum.text = posts[position].liked.toString()

                if (posts[position].is_bookmark) {
                    bookmarkBtn.setImageResource(R.drawable.ic_list_bookmark_active_x3)
                } else bookmarkBtn.setImageResource(R.drawable.ic_list_bookmark_inactive_x3)

                holder.itemView.setOnClickListener {
                    itemListener.onPostClick(it, position)
                }
//                setOnClickListener { itemListener.onPostClick(posts[position]!!.id) }

//                holder.itemView.bookmarkBtn.setOnClickListener {
//                    itemListener.onBookmarkButtonClick(it,posts[position]!!.id, posts[position]!!.is_bookmark, position)
//                }
                bookmarkBtn.setOnClickListener {
                    itemListener.onBookmarkButtonClick(
                        bookmarkBtn,
                        posts[position].post_id,
                        posts[position].is_bookmark,
                        position
                    )
                }

            }
        }
    }
//    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
////        private var view: View = itemView
////
////        fun bind(item: Post, position: Int) {
////
////            var tagString = ""
////            for (tag in item.tags) {
////                tagString += "#$tag "
////            }
////
////            view.apply {
////                title.text = item.title
////                userName.text = item.profile_nickname
////                date.text = item.created_at
////                tags.text = tagString
////                dislikeNum.text = item.hate_count.toString()
////                likeNum.text = item.good_count.toString()
//////                setOnClickListener { itemListener.onPostClick(item.id) }
//////                bookmarkBtn.setOnClickListener {
//////                    itemListener.onBookmarkButtonClick(view, item.id, item.is_bookmark, position)
//////                }
////                if (item.is_bookmark) {
////                    bookmarkBtn.setImageResource(R.drawable.ic_list_bookmark_active_x3)
////                }
////            }
//
////            Glide.with(view)
////                .load(item.attach_url)
////                .into(view.img)
////        }
//    }

    object Constant {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

}