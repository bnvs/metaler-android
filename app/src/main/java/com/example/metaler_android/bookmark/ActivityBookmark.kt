package com.example.metaler_android.bookmark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.metaler_android.R

class ActivityBookmark : AppCompatActivity(), ContractBookmark.View {

    private val TAG = "ActivityBookmark"

    override lateinit var presenter: ContractBookmark.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)
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
}
