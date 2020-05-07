package com.bnvs.metaler.ui.myposts

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
import com.bnvs.metaler.data.myposts.model.MyPost
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_my_posts_rv.view.*

class MyPostsAdapter(
    private var myPostsItemListener: MyPostsItemListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context

    private var myPostsList = mutableListOf<MyPost?>()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    object Constant {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    // 데이터를 처음 가져올 때 쓰는 함수
    fun addPosts(list: List<MyPost>) {
        this.myPostsList.addAll(list)
        notifyDataSetChanged()
    }


    // 다음 페이지 데이터를 가져오는 함수
    fun addMorePosts(list: ArrayList<MyPost?>) {
        this.myPostsList.addAll(list)
        notifyDataSetChanged()
    }

    fun resetList() {
        myPostsList.removeAll(myPostsList)
    }

    fun getItemAtPosition(position: Int): String? {
        return myPostsList[position].toString()
    }

    fun addLoadingView() {
        //add loading item
        Handler().post {
            myPostsList.add(null)
            notifyItemInserted(myPostsList.size - 1)
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        if (myPostsList.size != 0) {
            myPostsList.removeAt(myPostsList.size - 1)
            notifyItemRemoved(myPostsList.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_my_posts_rv, parent, false)
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
        return myPostsList.size
    }

    override fun getItemViewType(position: Int): Int {
        //null값이 들어오는지 안들어오는지 확인함. null값이 들어있으면 로딩뷰로 리턴한다.
        val comparable = myPostsList[position]
        return when (comparable) {
            null -> Constant.VIEW_TYPE_LOADING
            else -> Constant.VIEW_TYPE_ITEM
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
            if (myPostsList[position] != null) {
                var tagString = ""

                for (tag in myPostsList[position]!!.tags) {
                    tagString += "#${tag.name} "
                }

                holder.itemView.apply {
                    title.text = myPostsList[position]!!.title
                    userName.text = myPostsList[position]!!.profile_nickname
                    date.text = myPostsList[position]!!.date
                    tagName.text = tagString
                    dislikeNum.text = myPostsList[position]!!.disliked.toString()
                    likeNum.text = myPostsList[position]!!.liked.toString()

//                    if (!myPostsList[position]!!.thumbnail.isEmpty()) {
//                        Log.d("마이포스트어댑터","url ? : ${myPostsList[position]!!.thumbnail[0].url}")
//                        Glide.with(this)
//                            .asBitmap()//gif 재생안되고 첫번째 프레임에서 멈추도록 강제함
//                            .load(myPostsList[position]!!.thumbnail[0].url)
//                            .transform(CenterCrop(), RoundedCorners(24))
//                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                            .into(img)
//                    } else {
//                        Glide.with(this)
//                            .load(R.drawable.rounding_img_view)
//                            .into(img)
//                    }


                    holder.itemView.setOnClickListener {
                        myPostsItemListener.onPostClick(position)
                    }

                    moreBtn.setOnClickListener {
                        myPostsItemListener.onMoreButtonClick(
                            moreBtn,
                            myPostsList[position]!!.id,
                            position
                        )
                    }

                }
            }

        }
    }

}