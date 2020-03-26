package com.bnvs.metaler.mypage

import android.content.Context
import com.bnvs.metaler.data.job.source.JobDataSource
import com.bnvs.metaler.data.job.source.JobRepository
import com.bnvs.metaler.data.nickname.source.NicknameDataSource
import com.bnvs.metaler.data.nickname.source.NicknameRepository

class PresenterMyPage(
    private val context: Context,
    private val view: ContractMyPage.View) : ContractMyPage.Presenter {

    private val nicknameRepository: NicknameRepository = NicknameRepository(context)
    private val jobRepository: JobRepository = JobRepository(context)


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
        jobRepository.modifyJob(object : JobDataSource.ModifyJobCallback{
            override fun onJobModified() {
                view.showSuccessDialog()
            }

            override fun onDataNotAvailable() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun modifyNickName() {
        nicknameRepository.modifyNickname(object : NicknameDataSource.ModifyNicknameCallback{
            override fun onNicknameModified() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataNotAvailable() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
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
