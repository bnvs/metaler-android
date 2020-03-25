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

    override fun showPostDetailUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMaterialsList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showManufacturesList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showBookmarkDeleteDialog() {
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

}
