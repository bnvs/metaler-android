package com.bnvs.metaler.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bnvs.metaler.R
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.ui.myposts.ContractMyPosts

class ActivitySearch : AppCompatActivity(), ContractSearch.View {

    private val TAG = "ActivitySearch"

    override lateinit var presenter: ContractSearch.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        presenter = PresenterSearch(
            this@ActivitySearch,
            this@ActivitySearch
        )
    }

    override fun showPosts(posts: List<Post>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMorePosts(posts: List<Post>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeLoadingView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setRVScrollListener() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRefresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showRefreshPosts(posts: List<Post>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideError404() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError404() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
