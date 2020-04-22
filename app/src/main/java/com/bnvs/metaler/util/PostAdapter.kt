package com.bnvs.metaler.util

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
import com.bnvs.metaler.data.posts.model.Post
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_posts_rv.view.*

class PostAdapter(
    private var itemListener: PostItemListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context

    // 서버와 통신해서 받아오는 Post타입의 데이터를 담는 변수
    // 어레이 리스트인 이유 : 무한스크롤할 때 로딩중이면 null값을 추가하고, 로딩이끝나면 null값을 빼기 때문에 수정이 가능한 어레이리스트가 필요함
    var tempArrayList = ArrayList<Post?>()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // 데이터를 처음 가져올 때 쓰는 함수
    fun addPosts(list: List<Post>) {
        this.tempArrayList.addAll(list)
        notifyDataSetChanged()
    }


    // 다음 페이지 데이터를 가져오는 함수
    fun addMorePosts(list: ArrayList<Post?>) {
        this.tempArrayList.addAll(list)
        notifyDataSetChanged()
    }


    fun setBookmark(position: Int) {
        tempArrayList[position]!!.is_bookmark = !tempArrayList[position]!!.is_bookmark
    }

    fun getItemAtPosition(position: Int): String? {
        return tempArrayList[position].toString()
    }

    fun addLoadingView() {
        //add loading item
        Handler().post {
            tempArrayList.add(null)
            notifyItemInserted(tempArrayList.size - 1)
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        if (tempArrayList.size != 0) {
            tempArrayList.removeAt(tempArrayList.size - 1)
            notifyItemRemoved(tempArrayList.size)
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
//        Log.d("어댑터", "123123 getItemCount ? : ${posts.size}")
        return tempArrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        //null값이 들어오는지 안들어오는지 확인함. null값이 들어있으면 로딩뷰로 리턴한다.
        val comparable = tempArrayList[position]
        return when (comparable) {
            null -> Constant.VIEW_TYPE_LOADING
            else -> Constant.VIEW_TYPE_ITEM
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
            if (tempArrayList[position] != null) {
                var tagString = ""
                for (tag in tempArrayList[position]!!.tags) {
                    tagString += "#$tag "
                }


                holder.itemView.apply {
                    title.text = tempArrayList[position]!!.title
                    date.text = tempArrayList[position]!!.date
                    tags.text = tagString
                    dislikeNum.text = tempArrayList[position]!!.disliked.toString()
                    likeNum.text = tempArrayList[position]!!.liked.toString()

                    if (tempArrayList[position]!!.is_bookmark) {
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
                            tempArrayList[position]!!.post_id,
                            tempArrayList[position]!!.is_bookmark,
                            position
                        )
                    }

                }
            }

        }
    }


    object Constant {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

}