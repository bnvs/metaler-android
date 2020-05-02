package com.bnvs.metaler.ui.detail.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.comments.model.Comment
import com.bnvs.metaler.data.postdetails.model.PostDetails
import com.bnvs.metaler.ui.detail.listener.CommentMenuListener
import com.bnvs.metaler.ui.detail.listener.PostRatingListener
import com.bnvs.metaler.ui.detail.viewholder.*

class PostDetailAdapter(
    private val postDetails: PostDetails,
    private val ratingListener: PostRatingListener,
    private val menuListener: CommentMenuListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_CONTENT = 0
        private const val TYPE_PRICE = 1
        private const val TYPE_COMMENT_COUNT = 2
        private const val TYPE_COMMENT = 3
        private const val TYPE_LOADING_COMMENT = 4
        private const val TYPE_COMMENT_BOTTOM = 5
    }

    private val comments = mutableListOf<Comment?>()

    fun setComments(comments: List<Comment>) {
        this.comments.apply {
            clear()
            addAll(comments)
        }
        notifyDataSetChanged()
    }

    fun addComment(comment: Comment) {
        this.comments.add(comment)
        postDetails.comment_count += 1
        notifyDataSetChanged()
    }

    fun addComments(comments: List<Comment>) {
        this.comments.addAll(comments)
        notifyDataSetChanged()
    }

    fun deleteComment(commentIndex: Int) {
        this.comments.removeAt(commentIndex)
        postDetails.comment_count -= 1
        notifyDataSetChanged()
    }

    fun likePost() {
        this.postDetails.apply {
            rating = 1
        }
        notifyDataSetChanged()
    }

    fun cancelLikePost() {
        this.postDetails.apply {
            rating = 0
        }
        notifyDataSetChanged()
    }

    fun dislikePost() {
        this.postDetails.apply {
            rating = -1
        }
        notifyDataSetChanged()
    }

    fun cancelDislikePost() {
        this.postDetails.apply {
            rating = 0
        }
        notifyDataSetChanged()
    }

    fun addBookmark() {
        this.postDetails.is_bookmark = true
        notifyDataSetChanged()
    }

    fun deleteBookmark() {
        this.postDetails.is_bookmark = false
        notifyDataSetChanged()
    }

    fun setCommentsLoadingView(b: Boolean) {
        if (b) {
            android.os.Handler().post {
                this.comments.add(null)
                Log.d("setCommentsLoadingView : asdfasdfasdf", comments.toString())
                notifyItemInserted(comments.size + 2)
            }
        } else {
            if (this.comments[comments.size - 1] == null) {
                this.comments.removeAt(comments.size - 1)
                notifyItemRemoved(comments.size + 3)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_CONTENT -> {
                val inflatedView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_detail_content, parent, false)
                return ContentViewHolder(inflatedView)
            }
            TYPE_PRICE -> {
                val inflatedView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_detail_price, parent, false)
                return PriceViewHolder(inflatedView, ratingListener)
            }
            TYPE_COMMENT_COUNT -> {
                val inflatedView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_detail_comment_count, parent, false)
                return CommentCountViewHolder(inflatedView)
            }
            TYPE_COMMENT -> {
                val inflatedView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_detail_comment_rv, parent, false)
                return CommentViewHolder(inflatedView, menuListener)
            }
            TYPE_COMMENT_BOTTOM -> {
                val inflatedView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_detail_comment_bottom, parent, false)
                return CommentBottomViewHolder(inflatedView, menuListener)
            }
            else -> {
                val inflatedView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_loading, parent, false)
                return CommentLoadingViewHolder(inflatedView)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (comments.size > 0) {
            comments.size + 3
        } else {
            2
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (postDetails.comment_count > 0) {
            when (position) {
                0 -> TYPE_CONTENT
                1 -> TYPE_PRICE
                2 -> TYPE_COMMENT_COUNT
                postDetails.comment_count + 2 -> TYPE_COMMENT_BOTTOM
                else -> {
                    if (comments[position - 3] == null) {
                        TYPE_LOADING_COMMENT
                    } else TYPE_COMMENT
                }
            }
        } else {
            when (position) {
                0 -> TYPE_CONTENT
                else -> TYPE_PRICE
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_CONTENT -> {
                val contentViewHolder = holder as ContentViewHolder
                contentViewHolder.bind(postDetails)
            }
            TYPE_PRICE -> {
                val priceViewHolder = holder as PriceViewHolder
                priceViewHolder.bind(postDetails)
            }
            TYPE_COMMENT_COUNT -> {
                val commentCountViewHolder = holder as CommentCountViewHolder
                commentCountViewHolder.bind(postDetails.comment_count)
            }
            TYPE_COMMENT -> {
                val commentViewHolder = holder as CommentViewHolder
                commentViewHolder.bind(comments[position - 3]!!)
            }
            TYPE_COMMENT_BOTTOM -> {
                val commentBottomViewHolder = holder as CommentBottomViewHolder
                commentBottomViewHolder.bind(comments[position - 3]!!)
            }

        }
    }
}
