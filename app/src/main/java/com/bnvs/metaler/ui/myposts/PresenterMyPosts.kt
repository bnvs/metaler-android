package com.bnvs.metaler.ui.myposts

import android.content.Context
import android.widget.Toast
import com.bnvs.metaler.data.myposts.model.MyPosts
import com.bnvs.metaler.data.myposts.model.MyPostsRequest
import com.bnvs.metaler.data.myposts.source.repository.MyPostsRepository
import com.bnvs.metaler.network.NetworkUtil

class PresenterMyPosts(
    private val context: Context,
    private val view: ContractMyPosts.View
) : ContractMyPosts.Presenter {

    private val myPostsRepository: MyPostsRepository = MyPostsRepository()
    private lateinit var myPostsRequest: MyPostsRequest

    private var pageNum: Int = 0
    private var categoryType: String = ""

    init {
        view.presenter = this
    }

    override fun start() {
        loadMyPosts(requestPosts("manufacture"))
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

    override fun openMaterialsList() {
        pageNum = 0
        loadMyPosts(requestPosts("manufacture"))
    }

    override fun openManufacturesList() {
        pageNum = 0
        loadMyPosts(requestPosts("materials"))
    }

    override fun openPostDetail(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}