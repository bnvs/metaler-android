package com.bnvs.metaler.ui.myposts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.myposts.model.MyPost
import com.bnvs.metaler.util.EndlessRecyclerViewScrollListener

class ActivityMyPosts : AppCompatActivity(), ContractMyPosts.View {

    private val TAG = "ActivityMyPosts"

    override lateinit var presenter: ContractMyPosts.Presenter

    lateinit var myPostAdapter: MyPostsAdapter
    lateinit var scrollListener: EndlessRecyclerViewScrollListener
    lateinit var myPostLayoutManager: RecyclerView.LayoutManager
    var loadMoreMyPosts: MutableList<MyPost?> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_posts)

        presenter = PresenterMyPosts(
            this@ActivityMyPosts,
            this@ActivityMyPosts
        )

        presenter.run {
            start()
        }
    }


    //리사이클러뷰
    private fun setRVLayoutManager() {

    }

    override fun showMyPostsList(myPosts: List<MyPost>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPostDetailUi(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
