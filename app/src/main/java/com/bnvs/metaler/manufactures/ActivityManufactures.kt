package com.bnvs.metaler.manufactures

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.postsdummy.PostDummyData
import com.bnvs.metaler.data.postsdummy.PostsDummy
import kotlinx.android.synthetic.main.activity_manufacture.*
import kotlinx.android.synthetic.main.item_posts_rv.view.*

class ActivityManufactures : AppCompatActivity(), ContractManufactures.View {

    private val TAG = "ActivityManufactures"

    override lateinit var presenter: ContractManufactures.Presenter

    /**
     * 가공 탭에서 보여지는 가공 게시물 리사이클러뷰 아이템에 달아줄 클릭리스너입니다
     * onPostClick -> 게시물을 클릭한 경우
     * onBookmarkButtonClick -> 북마크 버튼을 클릭한 경우
     * */
    private var itemListener: ManufacturesPostItemListener = object : ManufacturesPostItemListener {
        override fun onPostClick(view: View, clickedPostId: Int) {
            Log.d(TAG, "눌린 아이템? : $clickedPostId")
            Log.d(TAG, "눌린 아이템? : ${posts[clickedPostId]!!.title}")
//            presenter.openPostDetail(clickedPostId)
        }
        override fun onBookmarkButtonClick(
            view: View,
            clickedPostId: Int,
            isBookmark: Boolean,
            position: Int
        ) {

//            var isBookmark = posts[position]!!.is_bookmark

            if (!isBookmark) {
            Log.d(TAG, "isBookmark ? : ${isBookmark}")

//            Log.d(TAG, "북마크버튼 눌린 아이템? : $position, isBookmark ? : ${posts[position]!!.is_bookmark}")
//            if(!posts[position]!!.is_bookmark) {

                Log.d(TAG, "북마크 이미지 변경!  ")
                Log.d(TAG, "clickedPostId ? : ${clickedPostId}")
                Log.d(TAG, "position ? : ${position}")

//                Log.d(TAG, "북마크버튼 눌린 아이템? : $position, isBookmark ? : $isBookmark")
//                postAdapter.setBookmark(position)

//                view.bookmarkBtn.setImageResource(R.drawable.ic_list_bookmark_active_x3)

//                isBookmark == posts[position]!!.is_bookmark
//                presenter.addBookmark(clickedPostId)
                postAdapter.apply {
                    setBookmark(position)
                    notifyDataSetChanged()
                    Log.d(TAG, "isBookmark ? : ${isBookmark}")

                }
            } else {
//                view.bookmarkBtn.setImageResource(R.drawable.ic_list_bookmark_inactive_x3)

//                isBookmark == !posts[position]!!.is_bookmark
//                presenter.deleteBookmark(clickedPostId)
                postAdapter.apply {
                    setBookmark(position)
                    notifyDataSetChanged()
                    Log.d(TAG, "isBookmark ? : ${isBookmark}")

                }
            }
        }

    }
//
//    private val postAdapter = PostAdapter(ArrayList(0), itemListener)
//    private val postLayoutManager = LinearLayoutManager(this)


    lateinit var posts: ArrayList<PostDummyData?>
    lateinit var loadMorePosts: ArrayList<PostDummyData?>
    lateinit var postAdapter: ManufacturesPostAdapter
    lateinit var scrollListener: EndlessRecyclerViewScrollListener
    lateinit var postLayoutManager: RecyclerView.LayoutManager

//    lateinit var itemListener: ManufacturesPostItemListener

    private var postsDummy: PostsDummy = PostsDummy()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manufacture)

        // Create the presenter
        presenter = PresenterManufactures(this, this)

        // Set up Buttons
        initClickListeners()

        // Set up posts recyclerView
//        postsRV.apply {
//            adapter = postAdapter
//            layoutManager = postLayoutManager
//        }

        // Set up RefreshListener
//        refreshLayout.apply {
        /*setOnRefreshListener {
            presenter.refreshPosts()
            refreshLayout.isRefreshing = false
        }
        setColorSchemeColors(
            ContextCompat.getColor(this@ActivityManufactures, R.color.colorPurple)
        )*/
//        }


        //더미데이터불러오기
        setPostsDummy()

        setAdapter()

        setRVLayoutManager()

        setRVScrollListener()

        // 가공 탭 presenter 시작
        presenter.run {
            start()
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }


    //리사이클러뷰에 보여줄 더미데이터를 가져온다.
    private fun setPostsDummy() {
        posts = ArrayList()

        // 북마크 아이템 한 개 클릭했는데 여러개 클릭 되는 에러 원인 : 테스트용으로 더미데이터 추가할 때 잘못된 듯.
        // for문으로 추가한 게 모두 하나의 아이템으로 묶여있었다.. 데이터를 하드코딩으로 따로따로 추가해보니 북마크 동시에 안눌리고 각각 잘 눌림
//        var tempData: PostDummyData = postsDummy.getDummy()

        var tags = listOf("tag1","tag2")
        var tempData = PostDummyData (
            1,
            2,
            2,
            "title1",
            "content",
            2000,
            "cash",
            "2020.02.02",
            "2020.02.04",
            "헬로우",
            tags,
            10,
            5,
            false
        )
        var tempData1 = PostDummyData (
            1,
            2,
            2,
            "title2",
            "content",
            2000,
            "cash",
            "2020.02.02",
            "2020.02.04",
            "헬로우헬로우",
            tags,
            10,
            5,
            false
        )
        var tempData2 = PostDummyData (
            1,
            2,
            2,
            "title3",
            "content",
            2000,
            "cash",
            "2020.02.02",
            "2020.02.04",
            "헬로우헬로우헬로우",
            tags,
            10,
            5,
            false
        )
//        for (i in 0..6) {
            posts.add(tempData)
        posts.add(tempData1)
        posts.add(tempData2)
//        }

//        Log.d(TAG, "더미데이터 ? : ${posts}")
    }

    private fun setAdapter() {
        postAdapter = ManufacturesPostAdapter(posts, itemListener)
        postAdapter.notifyDataSetChanged()
        postsRV.adapter = postAdapter
    }

    private fun setRVLayoutManager() {
        postLayoutManager = LinearLayoutManager(this)
        postsRV.layoutManager = postLayoutManager
        postsRV.setHasFixedSize(true)
    }

    private fun setRVScrollListener() {
        postLayoutManager = LinearLayoutManager(this)
        scrollListener = EndlessRecyclerViewScrollListener(postLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            EndlessRecyclerViewScrollListener.OnLoadMoreListener {
            override fun onLoadMore() {
                LoadMoreData()
            }
        })
        postsRV.addOnScrollListener(scrollListener)
    }

    private fun LoadMoreData() {
        //Add the Loading View
        postAdapter.addLoadingView()
        //Create the loadMoreItemsCells Arraylist
        loadMorePosts = ArrayList()
        //Get the number of the current Items of the main Arraylist
        val start = postAdapter.itemCount
        //Load 16 more items
        val end = start + 6
        //Use Handler if the items are loading too fast.
        //If you remove it, the data will load so fast that you can't even see the LoadingView
        Handler().postDelayed({
            for (i in start..end) {
                //Get data and add them to loadMoreItemsCells ArrayList
                loadMorePosts.add(postsDummy.getDummy())
            }
            //Remove the Loading View
            postAdapter.removeLoadingView()
            //We adding the data to our main ArrayList
            postAdapter.setPosts(loadMorePosts)
            //Change the boolean isLoading to false
            scrollListener.setLoaded()
            //Update the recyclerView in the main thread
            postsRV.post {
                postAdapter.notifyDataSetChanged()
            }
        }, 3000)

    }

    override fun showPosts(posts: ArrayList<PostDummyData?>) {
        postAdapter.setPosts(posts)
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

    private fun initClickListeners() {
        setTitleBarButtons()
        setTapBarButtons()
    }

    private fun setTitleBarButtons() {
        // 글작성, 글검색 버튼 클릭 리스너 달아주기
    }


    private fun setTapBarButtons() {
        homeBtn.setOnClickListener { presenter.openHome(this, this) }
        materialsBtn.setOnClickListener { presenter.openMaterials(this, this) }
        manufactureBtn.setOnClickListener { presenter.openManufactures(this, this) }
        bookmarkBtn.setOnClickListener { presenter.openBookmarks(this, this) }
        myPageBtn.setOnClickListener { presenter.openMyPage(this, this) }
    }
}
