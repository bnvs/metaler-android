package com.bnvs.metaler.util

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
import com.bnvs.metaler.data.posts.Post
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_posts_rv.view.*

class PostAdapter(
//    private var posts: List<Post>,
//    private var tempArrayList: ArrayList<Post?>,
    private var itemListener: PostItemListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context

    // 서버와 통신해서 받아오는 Post타입의 데이터를 담는 변수
    lateinit var posts: List<Post>
    // 무한스크롤할 때 로딩중이면 null값을 추가하고, 로딩이끝나면 null값을 빼기 때문에 수정이 가능한 어레이리스트가 필요함
    var tempArrayList = ArrayList<Post?>()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // 데이터를 처음 가져올 때 쓰는 함수
    fun addPosts(list: List<Post>) {
        this.posts = list
        this.tempArrayList.addAll(list)
        notifyDataSetChanged()
    }


    // 다음 페이지 데이터를 가져오는 함수
    fun addMorePosts(list: ArrayList<Post?>) {
        this.tempArrayList.addAll(list)
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
            tempArrayList.add(null)
            notifyItemInserted(tempArrayList.size - 1)

            Log.d(
                "어댑터",
                "-----tempArrayList에 null값 추가! tempArrayList 사이즈 : ${tempArrayList.size} tempArrayList: ${tempArrayList} "
            )
            Log.d("어댑터", "----- posts: ${posts}")
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
        return posts.size
    }

    override fun getItemViewType(position: Int): Int {
        //tempArrayList[tempArrayList.size - 1] 이렇게해야 null값 확인할 수 있음 ..

        //tempArrayList 에서 null값이 추가된 인덱스는 postiion+1 해야 null값 들어간 인덱스에 접근할 수 있음 .
        val comparable = tempArrayList[position + 1]
        return when (comparable) {
            null -> Constant.VIEW_TYPE_LOADING
            else -> Constant.VIEW_TYPE_ITEM
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


    object Constant {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

}