package com.bnvs.metaler.view.myposts

import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.myposts.model.MyPost
import com.bnvs.metaler.data.myposts.model.MyPostsRequest
import com.bnvs.metaler.data.myposts.source.repository.MyPostsRepository
import com.bnvs.metaler.data.postdetails.source.repository.PostDetailsRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.base.postsrv.BasePostsRvViewModel

class ViewModelMyPosts(
    private val myPostsRepository: MyPostsRepository,
    private val postDetailsRepository: PostDetailsRepository
) : BasePostsRvViewModel<MyPost>() {

    private val _categoryType = MutableLiveData<String>().apply { value = "materials" }
    val categoryType: MutableLiveData<String> = _categoryType

    init {
        loadPosts()
    }

    override fun loadPosts() {
        myPostsRepository.getMyPosts(
            getMyPostsRequest(),
            onSuccess = { response ->
                setItemLoadingView(false)
                if (response.posts.isNullOrEmpty()) {
                    if (posts.value.isNullOrEmpty()) {
                        _errorVisibility.value = true
                    }
                } else {
                    _errorVisibility.value = false
                    _hasNextPage.value = response.is_next
                    _posts.value = posts.value?.plus(response.posts) ?: response.posts
                }
            },
            onFailure = { e ->
                setItemLoadingView(false)
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    private fun getMyPostsRequest(): MyPostsRequest {
        page++
        return MyPostsRequest(page, limit, categoryType.value ?: "materials")
    }

    override fun loadMorePosts() {
        _hasNextPage.value = false
        setItemLoadingView(true)
        val handler = android.os.Handler()
        handler.postDelayed({ loadPosts() }, 1000)
    }

    fun changeCategoryType(categoryType: String) {
        if (_categoryType.value != categoryType) {
            _categoryType.value = categoryType
            resetPage()
            loadPosts()
        }
    }

    fun openMyPostMenu() {

    }

    fun openPostFirstActivity() {

    }

    fun deletePost(postId: Int, position: Int) {
        postDetailsRepository.deletePost(
            postId,
            onSuccess = {
                posts.value?.filterIndexed { index, _ ->
                    index != position
                }.let {
                    _posts.value = it
                    if (posts.value.isNullOrEmpty()) {
                        _errorVisibility.value = true
                    }
                    _errorToastMessage.setMessage("게시물이 삭제되었습니다")
                }
            },
            onFailure = { e ->
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }
}