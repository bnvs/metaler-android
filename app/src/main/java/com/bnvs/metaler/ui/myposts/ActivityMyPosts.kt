package com.bnvs.metaler.ui.myposts

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.myposts.model.MyPost
import com.bnvs.metaler.util.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.activity_my_posts.*

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

        activeMaterialsCategoryBtn()

        setCategoryButtons()

        setRVAdapter()

        setRVLayoutManager()

        setRVScrollListener()
    }

    /**
     * 내가 쓴 글 확인 재료/가공 카테고리 눌렀을 때 보여지는 재료/가공 게시물 리사이클러뷰 아이템에 달아줄 클릭리스너입니다
     * 아이템 클릭 시, 클릭한 게시물의 post_id 를 presenter 에 전달합니다.
     * onPostClick -> 게시물을 클릭한 경우
     * onMoreButtonClick -> 더보기 버튼을 클릭한 경우. 수정, 삭제 메뉴 다이얼로그를 실행합니다.
     * */
    private var myPostsItemListener: MyPostsItemListener = object : MyPostsItemListener {
        override fun onPostClick(view: View, clickedPostId: Int) {
            presenter.openPostDetail(clickedPostId)
        }

        override fun onMoreButtonClick(view: View, clickedPostId: Int, position: Int) {
            val array = arrayOf("수정", "삭제")
            AlertDialog.Builder(this@ActivityMyPosts)
                .setItems(array) { _, which ->
                    when (array[which]) {
                        "수정" -> {
                            makeToast("$clickedPostId 를 수정합니다 ")
//                            presenter.openModifyComment()
                        }
                        "삭제" -> {
                            makeToast("$clickedPostId 를 삭제합니다 ")
//                            presenter.openDeleteComment()
                        }
                    }
                }
                .show()
        }
    }

    //리사이클러뷰
    private fun setRVAdapter() {
        myPostAdapter = MyPostsAdapter(myPostsItemListener)
    }

    private fun setRVLayoutManager() {
        myPostLayoutManager = LinearLayoutManager(this)
        postsRV.layoutManager = myPostLayoutManager
        postsRV.setHasFixedSize(true)
    }

    private fun setRVScrollListener() {
        myPostLayoutManager = LinearLayoutManager(this)
        scrollListener = EndlessRecyclerViewScrollListener(myPostLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
        EndlessRecyclerViewScrollListener.OnLoadMoreListener{
            override fun onLoadMore() {
                //loadMoreMyPosts 에 null값을 추가해서 로딩뷰를 만든다.
                myPostAdapter.addLoadingView()
                loadMoreMyPosts.add(null)

                //loadMoreMyPosts 리스트에는 다음페이지 데이터가 있을때만 데이터를 추가하기 때문에 조건절로 비어있는지 확인해야함
                if (!loadMoreMyPosts.isEmpty()) {
                    //loadMoreMyPosts 마지막 값이 null값이 있으면 무한스크롤 로딩 중이기 때문에 데이터를 받아오고, 로딩뷰를 제거한다.

                }

            }
        })
    }

    override fun showMyPostsList(myPosts: List<MyPost>) {
        Log.d(TAG,"myPosts? : $myPosts")
        myPostAdapter.addPosts(myPosts)
        myPostAdapter.notifyDataSetChanged()
        postsRV.adapter = myPostAdapter
        postsRV.visibility = View.VISIBLE
    }

    override fun hideError404() {
        error404Group.visibility = View.INVISIBLE
    }

    override fun showError404() {
        error404Group.visibility = View.VISIBLE
    }

    override fun showDeletePostDialog() {
        AlertDialog.Builder(this@ActivityMyPosts)
            .setTitle("게시글을 삭제하시겠습니까?")
            .setPositiveButton("확인") { dialog, which ->
//                presenter.deletePost()
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    override fun showCannotModifyRatedPostDialog() {
        makeAlertDialog("가격 평가가 진행된 게시물은 수정할 수 없습니다")
    }

    override fun setCategoryButtons() {
        materialsCategoryBtn.setOnClickListener {
            activeMaterialsCategoryBtn()
            myPostAdapter.resetList()
            presenter.openMaterialsList()
        }

        manufactureCategoryBtn.setOnClickListener {
            activeManufactureCategoryBtn()
            myPostAdapter.resetList()
            presenter.openManufacturesList()
        }
    }

    override fun activeMaterialsCategoryBtn() {
        materialsCategoryBtn.setTextColor(ContextCompat.getColor(this, R.color.colorPurple))
        materialsBar.visibility = View.VISIBLE
        manufactureCategoryBtn.setTextColor(ContextCompat.getColor(this, R.color.colorLightGrey))
        manufactureBar.visibility = View.INVISIBLE
    }

    override fun activeManufactureCategoryBtn() {
        manufactureCategoryBtn.setTextColor(ContextCompat.getColor(this, R.color.colorPurple))
        manufactureBar.visibility = View.VISIBLE
        materialsCategoryBtn.setTextColor(ContextCompat.getColor(this, R.color.colorLightGrey))
        materialsBar.visibility = View.INVISIBLE
    }

    private fun makeToast(message: String) {
        Toast.makeText(this@ActivityMyPosts, message, Toast.LENGTH_SHORT).show()
    }

    private fun makeAlertDialog(message: String) {
        AlertDialog.Builder(this@ActivityMyPosts)
            .setTitle("알림")
            .setMessage(message)
            .setPositiveButton("확인") { _, _ ->
            }
            .show()
    }
}
