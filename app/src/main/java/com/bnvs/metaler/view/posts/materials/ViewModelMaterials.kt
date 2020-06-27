package com.bnvs.metaler.view.posts.materials

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.model.DeleteBookmarkRequest
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.data.categories.source.repository.CategoriesRepository
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.data.posts.source.repository.PostsRepository
import com.bnvs.metaler.data.tags.model.TagsRequest
import com.bnvs.metaler.data.tags.source.repository.TagsRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.base.postsrvadvanced.BasePostsRvAdvancedViewModel
import com.bnvs.metaler.util.constants.POST_REQUEST_TYPE
import com.bnvs.metaler.util.constants.POST_REQUEST_WITH_SEARCH_TYPE_TAG

class ViewModelMaterials(
    private val postsRepository: PostsRepository,
    private val bookmarksRepository: BookmarksRepository,
    private val categoriesRepository: CategoriesRepository,
    private val tagsRepository: TagsRepository
) : BasePostsRvAdvancedViewModel<Post>() {

    private val TAG = "ViewModel Materials"

    // 카테고리 리스트 + 선택한 카테고리 아이디(category_id)
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories
    private val _selectedCategoryId = MutableLiveData<Int>()
    val selectedCategoryId: LiveData<Int> = _selectedCategoryId

    // 게시글 목록 요청 request 용 데이터
    override val categoryId = selectedCategoryId

    init {
        loadCategories()
    }

    override fun refresh() {
        super.refresh()
        loadPosts()
    }

    override fun refreshForOnResume() {
        super.refreshForOnResume()
        loadPosts()
    }

    private fun loadCategories() {
        categoriesRepository.getCategories(
            onSuccess = { response ->
                response
                    .filter { it.type == "total" || it.type == "materials" }
                    .let { _categories.value = it }
                response.first { it.type == "total" }.id.let {
                    _selectedCategoryId.value = it
                    setCategoryTypeCache(it)
                }
                loadPosts()
            },
            onFailure = { e ->
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    override fun setCategoryTypeCache(categoryId: Int) {
        categoriesRepository.saveCategoryTypeCache(categoryId)
    }

    fun changeSelectedCategory(categoryId: Int) {
        if (_selectedCategoryId.value != categoryId) {
            _selectedCategoryId.value = categoryId
            resetPage()
            loadPosts()
        }
    }

    override fun loadPosts() {
        when (postRequestType) {
            POST_REQUEST_TYPE -> loadPostsNormal()
            POST_REQUEST_WITH_SEARCH_TYPE_TAG -> loadPostsWithSearchTypeTag()
        }
        Log.d(TAG, "게시물 목록 가져오기 - postRequestType : $postRequestType")
    }

    override fun loadMorePosts() {
        _hasNextPage.value = false
        setItemLoadingView(true)
        val handler = android.os.Handler()
        handler.postDelayed({ loadPosts() }, 1000)
    }

    override fun loadPostsNormal() {
        postsRepository.getPosts(
            getPostsRequest(),
            onSuccess = { response ->
                setItemLoadingView(false)
                _isLoading.value = false
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
                _isLoading.value = false
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    override fun loadPostsWithSearchTypeTag() {
        postsRepository.getPostsWithSearchTypeTag(
            getPostsWithTagRequest(),
            onSuccess = { response ->
                setItemLoadingView(false)
                _isLoading.value = false
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
                _isLoading.value = false
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    override fun addBookmark(postId: Int, position: Int) {
        bookmarksRepository.addBookmark(
            AddBookmarkRequest(postId),
            onSuccess = { response ->
                posts.value?.mapIndexed { index, post ->
                    if (index == position) {
                        post?.copy(
                            bookmark_id = response.bookmark_id,
                            is_bookmark = true
                        )
                    } else {
                        post
                    }
                }.let {
                    _posts.value = it
                    _errorToastMessage.setMessage("북마크되었습니다")
                }
            },
            onFailure = { e ->
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    override fun deleteBookmark(bookmarkId: Int, position: Int) {
        bookmarksRepository.deleteBookmark(
            DeleteBookmarkRequest(bookmarkId),
            onSuccess = {
                posts.value?.mapIndexed { index, post ->
                    if (index == position) {
                        post?.copy(
                            bookmark_id = 0,
                            is_bookmark = false
                        )
                    } else {
                        post
                    }
                }.let {
                    _posts.value = it
                    _errorToastMessage.setMessage("북마크에서 삭제되었습니다")
                }
            },
            onFailure = { e ->
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    override fun getTagSuggestions(input: String) {
        val type = 1 //TODO: 태그 전체 검색시에는 타입 어떻게 설정해야하는지?
        tagsRepository.getTagRecommendations(
            TagsRequest(type, input, 10),
            onSuccess = { response ->
                _tagSuggestions.value = response
            },
            onFailure = { e ->
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            }
        )
    }
}