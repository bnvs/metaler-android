package com.example.metaler_android.manufactures

import android.content.Context
import com.example.metaler_android.data.post.source.PostRepository

class PresenterManufactures(
    private val context: Context,
    val view: ContractManufactures.View) : ContractManufactures.Presenter {

    private val postRepository: PostRepository = PostRepository(context)

    init {
        view.presenter = this
    }

    override fun start() {
        loadPosts()
    }

    override fun loadPosts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openPostDetail(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addBookmark(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteBookmark(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openSearch() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addSearchTag() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearSearchTagBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteSearchTag() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * TapBarContract.Presenter 에서 상속받은 함수
     * */
    override fun openHome() { view.showHomeUi(context) }
    override fun openMaterials() { view.showMaterialsUi(context) }
    override fun openManufactures() { view.showManufacturesUi(context) }
    override fun openBookmarks() { view.showBookmarksUi(context) }
    override fun openMyPage() { view.showMyPageUi(context) }

}