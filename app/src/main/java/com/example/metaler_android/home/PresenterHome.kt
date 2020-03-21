package com.example.metaler_android.home

import android.content.Context
import com.example.metaler_android.data.homepost.HomePosts
import com.example.metaler_android.data.homepost.source.HomePostDataSource
import com.example.metaler_android.data.homepost.source.HomePostRepository
import com.example.metaler_android.data.profile.Profile
import com.example.metaler_android.data.profile.source.ProfileDataSource
import com.example.metaler_android.data.profile.source.ProfileRepository

class PresenterHome(context: Context, val view: ContractHome.View) : ContractHome.Presenter {

    private val profileRepository: ProfileRepository = ProfileRepository(context)
    private val homePostRepository: HomePostRepository = HomePostRepository(context)

    init {
        view.presenter = this
    }

    override fun start() {
        loadProfile()
        loadHomePost()
    }

    override fun loadProfile() {
        profileRepository.getProfile(object : ProfileDataSource.LoadProfileCallback {
            override fun onProfileloaded(profile: Profile) {
                view.showProfile(profile)
            }

            override fun onFailure() {
                
            }

        })
    }

    override fun loadHomePost() {
        homePostRepository.getHomePosts(object : HomePostDataSource.LoadHomePostsCallback {
            override fun onHomePostsLoaded(homePosts: HomePosts) {
                view.showMaterialsList(homePosts.materials)
                view.showManufacturesList(homePosts.manufactures)
            }

            override fun onDataNotAvailable() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    override fun openMaterials() {
        view.showMaterialsUi()
    }

    override fun openManufactures() {
        view.showManufacturesUi()
    }

    override fun openPostDetail(postId: Int) {
        view.showPostDetailUi(postId)
    }

    override fun setTapBar(context: Context) {
        view.setTapBarListener(context)
    }
}