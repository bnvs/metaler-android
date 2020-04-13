package com.bnvs.metaler.ui.mypage

import android.content.Context

class PresenterMyPage(
    private val context: Context,
    private val view: ContractMyPage.View
) : ContractMyPage.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        loadProfile()
    }

    override fun loadProfile(){

    }

    override fun logout() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun modifyJob() {

    }

    override fun modifyNickName() {

    }

    override fun withdrawal() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openTerms() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openJobModify() { view.showJobModifyUi() }

    override fun openMyPosts() { view.showMyPostsUi() }

    override fun openNicknameModify() { view.showNicknameModifyDialog() }

    override fun openLogout() { view.showLogoutDialog() }

    override fun openWithdrawal() { view.showWithdrawalDialog() }

}
