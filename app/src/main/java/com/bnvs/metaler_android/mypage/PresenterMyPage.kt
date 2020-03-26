package com.bnvs.metaler_android.mypage

import android.content.Context

class PresenterMyPage(
    private val context: Context,
    private val view: ContractMyPage.View) : ContractMyPage.Presenter {

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

    override fun openNicknameModify() { view.showNicknameModifyDialog() }

    override fun openLogout() { view.showLogoutDialog() }

    override fun openWithdrawal() { view.showWithdrawalDialog() }

}
