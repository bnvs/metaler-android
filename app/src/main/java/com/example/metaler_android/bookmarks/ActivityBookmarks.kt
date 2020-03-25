package com.example.metaler_android.bookmarks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.metaler_android.R
import com.example.metaler_android.home.PresenterHome
import kotlinx.android.synthetic.main.activity_bookmark.*
import kotlinx.android.synthetic.main.activity_bookmark.bookmarkBtn
import kotlinx.android.synthetic.main.activity_bookmark.homeBtn
import kotlinx.android.synthetic.main.activity_bookmark.manufactureBtn
import kotlinx.android.synthetic.main.activity_bookmark.materialsBtn
import kotlinx.android.synthetic.main.activity_bookmark.myPageBtn
import kotlinx.android.synthetic.main.activity_home.*

class ActivityBookmarks : AppCompatActivity(), ContractBookmarks.View {

    private val TAG = "ActivityBookmarks"

    override lateinit var presenter: ContractBookmarks.Presenter


    /**
     * 북마크 재료 카테고리 눌렀을 때 보여지는 재료 게시물 리사이클러뷰 아이템에 달아줄 클릭리스너입니다
     * 아이템 클릭 시, 클릭한 게시물의 post_id 를 presenter 에 전달합니다.
     * */
    private var itemListener: PostItemListener = object : PostItemListener {
        override fun onPostClick(clickedPostId: Int) {
            presenter.openPostDetail(clickedPostId)
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        // Create the presenter
        presenter = PresenterBookmarks(
            this@ActivityBookmarks,
            this@ActivityBookmarks
        )

        initClickListeners()

        presenter.run {
            start()
        }

    }

    override fun showPostDetailUi(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMaterialsList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showManufacturesList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showBookmarkDeleteDialog(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initClickListeners() {
        setCategoryButtons()
        setTapBarButtons()
    }

    private fun setCategoryButtons(){
        materialsCategoryBtn.setOnClickListener { presenter.openMaterialsList() }
        manufactureCategoryBtn.setOnClickListener { presenter.openManufacturesList() }
    }

    private fun setTapBarButtons() {
        homeBtn.setOnClickListener { presenter.openHome(this, this) }
        materialsBtn.setOnClickListener { presenter.openMaterials(this, this) }
        manufactureBtn.setOnClickListener { presenter.openManufactures(this, this) }
        bookmarkBtn.setOnClickListener { presenter.openBookmarks(this, this) }
        myPageBtn.setOnClickListener { presenter.openMyPage(this, this) }
    }


    private interface PostItemListener {
        fun onPostClick(clickedPostId: Int)
    }

}
