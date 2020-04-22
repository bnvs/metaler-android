package com.bnvs.metaler.ui.postfirst

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bnvs.metaler.R

class ActivityPostFirst : AppCompatActivity(), ContractPostFirst.View {

    private val TAG = "ActivityPostFirst"

    override lateinit var presenter: ContractPostFirst.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_first)

        val categoryType = intent.getStringExtra("CATEGORY_TYPE")
        val postId = intent.getIntExtra("POST_ID", 0)

        presenter = PresenterPostFirst(categoryType, postId, this)

        presenter.run {

        }
    }

    override fun showCategories() {
    }

    override fun showPostDetailLoadFailedDialog(errorMessage: String) {

    }

    override fun showImageDeleteDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyTitleDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyPriceDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyPriceTypeDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyContentsDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPostSecondUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
