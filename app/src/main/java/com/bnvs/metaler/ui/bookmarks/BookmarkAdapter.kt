package com.bnvs.metaler.ui.bookmarks

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
import com.bnvs.metaler.data.bookmarks.model.Bookmark
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.item_bookmark_rv.view.*
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_posts_rv.view.*
import kotlinx.android.synthetic.main.item_posts_rv.view.date
import kotlinx.android.synthetic.main.item_posts_rv.view.dislikeNum
import kotlinx.android.synthetic.main.item_posts_rv.view.img
import kotlinx.android.synthetic.main.item_posts_rv.view.likeNum
import kotlinx.android.synthetic.main.item_posts_rv.view.title

class BookmarkAdapter(
    private var bookmarkItemListener: BookmarkPostItemListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context

    // 서버와 통신해서 받아오는 Bookmark타입의 데이터를 담는 변수
    // 어레이 리스트인 이유 : 무한스크롤할 때 로딩중이면 null값을 추가하고, 로딩이끝나면 null값을 빼기 때문에 수정이 가능한 어레이리스트가 필요함
    var tempArrayList = ArrayList<Bookmark?>()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // 데이터를 처음 가져올 때 쓰는 함수
    fun addPosts(list: List<Bookmark>) {
        this.tempArrayList.addAll(list)
        notifyDataSetChanged()
    }


    // 다음 페이지 데이터를 가져오는 함수
    fun addMorePosts(list: ArrayList<Bookmark?>) {
        this.tempArrayList.addAll(list)
        notifyDataSetChanged()
    }

    fun resetList() {
        tempArrayList.removeAll(tempArrayList)
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
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_bookmark_rv, parent, false)
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
                    tagString += "#${tag.name} "
                }


                holder.itemView.apply {
                    title.text = tempArrayList[position]!!.title
                    date.text = tempArrayList[position]!!.date
                    tags?.text = tagString
                    dislikeNum.text = tempArrayList[position]!!.dis_liked.toString()
                    likeNum.text = tempArrayList[position]!!.liked.toString()

                    if (!tempArrayList[position]!!.thumbnail.isEmpty()) {
                        Glide.with(this)
                            .asBitmap()//gif 재생안되고 첫번째 프레임에서 멈추도록 강제함
                            .load("http://file.metaler.kr/upload/"+tempArrayList[position]!!.thumbnail)
                            .transform(CenterCrop(), RoundedCorners(24))
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(img)
                    } else {
                        Glide.with(this)
                            .load(R.drawable.rounding_img_view)
                            .into(img)
                    }


                    holder.itemView.setOnClickListener {
                        bookmarkItemListener.onPostClick(position)
                    }

                    deleteBtn.setOnClickListener {
                        bookmarkItemListener.onDeleteButtonClick(
                            bookmarkBtn,
                            tempArrayList[position]!!.post_id,
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