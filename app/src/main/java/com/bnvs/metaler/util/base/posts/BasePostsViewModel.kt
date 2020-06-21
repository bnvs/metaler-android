package com.bnvs.metaler.util.base.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.util.base.tap.BaseTapViewModel

abstract class BasePostsViewModel : BaseTapViewModel() {

    // 상세 페이지 + post_id
    private val _openDetailActivity = MutableLiveData<Boolean>().apply { value = false }
    val openDetailActivity: LiveData<Boolean> = _openDetailActivity
    private val _postId = MutableLiveData<Int>()
    val postId: LiveData<Int> = _postId

    protected val _errorVisibility = MutableLiveData<Boolean>().apply { value = false }
    val errorVisibility: LiveData<Boolean> = _errorVisibility

    fun openPostDetail(postId: Int) {
        _postId.value = postId
        startDetailActivity()
    }

    private fun startDetailActivity() {
        _openDetailActivity.enable()
    }

}