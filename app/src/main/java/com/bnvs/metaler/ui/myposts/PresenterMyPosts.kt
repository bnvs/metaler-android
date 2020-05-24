package com.bnvs.metaler.ui.myposts

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.bnvs.metaler.data.myposts.model.MyPosts
import com.bnvs.metaler.data.myposts.model.MyPostsRequest
import com.bnvs.metaler.data.myposts.source.repository.MyPostsRepository
import com.bnvs.metaler.data.postdetails.source.repository.PostDetailsRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.ui.detail.ActivityDetail

class PresenterMyPosts(
    private val context: Context,
    private val view: ContractMyPosts.View
) : ContractMyPosts.Presenter {

    private val postDetailsRepository = PostDetailsRepository()
    private val myPostsRepository: MyPostsRepository = MyPostsRepository()
    private lateinit var myPostsRequest: MyPostsRequest

    private var pageNum: Int = 0
    private var categoryType: String = ""

    init {
        view.presenter = this
    }

    override fun start() {
        openMaterialsList()
    }

    override fun requestPosts(categoryType: String): MyPostsRequest {
        pageNum++
        this.categoryType = categoryType
        myPostsRequest = MyPostsRequest(
            pageNum,
            10,
            categoryType
        )
        return myPostsRequest
    }

    override fun loadMyPosts(myPostsRequest: MyPostsRequest) {
        myPostsRepository.getMyPosts(
            myPostsRequest,
            onSuccess = { response: MyPosts ->
                if (response.posts.isEmpty()) {
                    view.showError404()
                } else
                    view.hideError404()
                view.showMyPostsList(response.posts)
            },
            onFailure = { e ->
                Toast.makeText(
                    context,
                    "서버 통신 실패 : ${NetworkUtil.getErrorMessage(e)}",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }

    //TODO : 카테고리 변경할 때마다 페이지번호를 리셋하는 것보단 상태를 저장하는게 나을듯
    override fun openMaterialsList() {
        pageNum = 0
        loadMyPosts(requestPosts("materials"))
    }

    override fun openManufacturesList() {
        pageNum = 0
        loadMyPosts(requestPosts("manufacture"))
    }

    override fun openPostDetail(postId: Int) {
        val detailIntent = Intent(context, ActivityDetail::class.java)
        detailIntent.putExtra("POST_ID", postId)
        context.startActivity(detailIntent)
    }

    override fun deletePost(clickedPostId: Int) {
        postDetailsRepository.deletePost(
            clickedPostId,
            onSuccess = {
                view.showPostDeletedToast()
            },
            onFailure = { e ->
                view.apply {
                    showErrorToast(
                        "게시글 삭제에 실패했습니다" +
                                "\n ${NetworkUtil.getErrorMessage(e)}"
                    )
                }
            }
        )
    }

    override fun modifyPost(clickedPostId: Int, likedNum: Int, dislikedNum: Int) {
        if (likedNum != 0 || dislikedNum != 0) {
            view.showCannotModifyRatedPostDialog()
        } else {
            view.openEditPostUi(clickedPostId)
        }
    }
}