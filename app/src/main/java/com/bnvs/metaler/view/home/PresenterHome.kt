package com.bnvs.metaler.view.home

import android.content.Context
import com.bnvs.metaler.data.homeposts.source.repository.HomePostsRepository
import com.bnvs.metaler.data.profile.source.local.ProfileLocalDataSourceImpl
import com.bnvs.metaler.data.profile.source.repository.ProfileRepositoryImpl
import com.bnvs.metaler.network.NetworkUtil

class PresenterHome(
    context: Context,
    private val view: ContractHome.View
) : ContractHome.Presenter {

    private val profileRepository = ProfileRepositoryImpl(
        ProfileLocalDataSourceImpl(context)
    )
    private val homePostRepository =
        HomePostsRepository(context)

    init {
        view.presenter = this
    }

    override fun start() {
        loadProfile()
        loadHomePost()
    }

    override fun loadProfile() {
        profileRepository.getProfile(
            onProfileLoaded = { profile ->
                view.showProfile(profile)
            },
            onProfileNotExist = {
                view.showProfileNotExistToast()
            }
        )
    }

    override fun loadHomePost() {
        homePostRepository.getHomePosts(
            onSuccess = { response ->
                view.showMaterialsList(response.materials)
                view.showManufacturesList(response.manufactures)
            },
            onFailure = { e ->
                view.showLoadHomePostFailedToast(NetworkUtil.getErrorMessage(e))
            }
        )
    }

    override fun openPostDetail(clickedHomePostId: Int) {
        view.showPostDetailUi(clickedHomePostId)
    }

    override fun setStatusBar() {
        view.setTransparentStatusBar()
    }

}