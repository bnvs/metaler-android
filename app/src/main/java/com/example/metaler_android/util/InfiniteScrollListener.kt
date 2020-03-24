package com.example.metaler_android.util

import androidx.recyclerview.widget.RecyclerView

abstract class InfiniteScrollListener : RecyclerView.OnScrollListener() {

    

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }
}