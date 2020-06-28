package com.bnvs.metaler.view.myposts

import androidx.lifecycle.LiveData
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
    val categoryType: LiveData<String> = _categoryType

    private val _openMyPostMenuDialog =
        MutableLiveData<Map<String, Int>>().apply { value = mapOf() }
    val openMyPostMenuDialog: LiveData<Map<String, Int>> = _openMyPostMenuDialog
    private val _openPostFirstActivity =
        MutableLiveData<Map<String, Int>>().apply { value = mapOf() }
    val openPostFirstActivity: LiveData<Map<String, Int>> = _openPostFirstActivity
    private val _openPostDeleteDialog =
        MutableLiveData<Map<String, Int>>().apply { value = mapOf() }
    val openPostDeleteDialog: LiveData<Map<String, Int>> = _openPostDeleteDialog

    private val _finishThisActivity = MutableLiveData<Boolean>().apply { value = false }
    val finishThisActivity: LiveData<Boolean> = _finishThisActivity

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

    fun openMyPostMenuDialog(postId: Int, position: Int) {
        _openMyPostMenuDialog.value = mapOf("postId" to postId, "position" to position)
    }

    fun openPostFirstActivity(postId: Int, position: Int) {
        if (posts.value?.get(position)?.update_available == true) {
            _openPostFirstActivity.value = mapOf("postId" to postId)
        } else {
            _errorDialogMessage.setMessage("가격 평가가 진행된 게시물은 수정할 수 없습니다")
        }

    }

    fun openDeletePostDialog(postId: Int, position: Int) {
        _openPostDeleteDialog.value = mapOf("postId" to postId, "position" to position)
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

    fun finishMyPostsActivity() {
        _finishThisActivity.enable()
    }
}