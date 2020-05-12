package com.bnvs.metaler.ui.myposts

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.myposts.model.MyPost
import com.bnvs.metaler.data.myposts.model.MyPostsRequest
import com.bnvs.metaler.util.TapBarContract

interface ContractMyPosts {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showMyPostsList(myPosts: List<MyPost>)

        fun hideError404()

        fun showError404()

        fun showPostDeletedToast()

        fun showCannotModifyRatedPostDialog()

        fun showDeletePostDialog(clickedPostId: Int)

        fun setCategoryButtons()

        fun activeMaterialsCategoryBtn()

        fun activeManufactureCategoryBtn()

        fun showErrorToast(errorMessage: String)

        fun finishActivity()
    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadMyPosts(myPostsRequest: MyPostsRequest)

        fun openMaterialsList()

        fun openManufacturesList()

        fun requestPosts(categoryType: String): MyPostsRequest

        fun deletePost(clickedPostId: Int)

        fun openPostDetail(postId: Int)
    }
}