package com.example.metaler_android.bookmarks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.metaler_android.R

class ActivityBookmarks : AppCompatActivity(), ContractBookmarks.View {

    private val TAG = "ActivityBookmarks"

    override lateinit var presenter: ContractBookmarks.Presenter

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
