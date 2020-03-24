package com.example.metaler_android.mypage

import android.content.Context

class PresenterMyPage(context: Context, val view: ContractMyPage.View) : ContractMyPage.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        loadProfile()
    }

    override fun loadProfile(){

    }

    override fun openJobModify() { view.showJobModifyUi() }

    override fun openMyPosts() { view.showMyPostsUi() }

    /**
     * TapBarContract.Presenter 에서 상속받은 함수
     * */
    override fun openHome() { view.showHomeUi() }
    override fun openMaterials() { view.showMaterialsUi() }
    override fun openManufactures() { view.showManufacturesUi() }
    override fun openBookmarks() { view.showBookmarksUi() }
    override fun openMyPage() { view.showMyPageUi() }
}
