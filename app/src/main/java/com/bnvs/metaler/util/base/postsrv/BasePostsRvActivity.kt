package com.bnvs.metaler.util.base.postsrv

import com.bnvs.metaler.util.base.posts.BasePostsActivity

abstract class BasePostsRvActivity<VM : BasePostsRvViewModel<ITEM>, ITEM> :
    BasePostsActivity<VM>() {

    protected open fun setListeners() {
        setRecyclerViewScrollListener()
    }

    protected abstract fun setRecyclerViewScrollListener()

}