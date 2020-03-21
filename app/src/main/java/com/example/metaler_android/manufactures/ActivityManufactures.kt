package com.example.metaler_android.manufactures

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manufacture)

        // Create the presenter
        presenter = PresenterManufactures(
            this@ActivityManufactures,
            this@ActivityManufactures
        )
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

    private class PostAdapter(
        var posts: List<Post>
    ) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

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

                view.title.text = item.title
                view.userName.text = item.nickname
                view.date.text = item.date
                view.dislikeNum.text = item.dis_like.toString()
                view.likeNum.text = item.like.toString()

                if (item.is_bookmark) {
                    view.bookmarkBtn.setImageResource(R.drawable.ic_list_bookmark_active_x3)
                }

                // view.tagRV.text = tags

                Glide.with(view)
                    .load(item.attach_url)
                    .into(view.img)
            }
        }

    }
}
