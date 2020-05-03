package com.bnvs.metaler.ui.myposts

import android.os.Bundle
import android.view.View
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

    /**
     * 내가 쓴 글 확인 재료/가공 카테고리 눌렀을 때 보여지는 재료/가공 게시물 리사이클러뷰 아이템에 달아줄 클릭리스너입니다
     * 아이템 클릭 시, 클릭한 게시물의 post_id 를 presenter 에 전달합니다.
     * onPostClick -> 게시물을 클릭한 경우
     * onMoreButtonClick -> 더보기 버튼을 클릭한 경우. 수정, 삭제 메뉴 다이얼로그를 실행합니다.
     * */
    private var myPostsItemListener: MyPostsItemListener = object : MyPostsItemListener {
        override fun onPostClick(clickedPostId: Int) {
            presenter.openPostDetail(clickedPostId)
        }

        override fun onMoreButtonClick(view: View, clickedPostId: Int, position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
