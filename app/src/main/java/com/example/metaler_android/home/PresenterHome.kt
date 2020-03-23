package com.example.metaler_android.home

import android.content.Context
import com.example.metaler_android.data.homeposts.source.HomePostsRepository
import com.example.metaler_android.data.profile.source.ProfileRepository

class PresenterHome(context: Context, val view: ContractHome.View) : ContractHome.Presenter {

    private val profileRepository: ProfileRepository = ProfileRepository(context)
    private val homePostRepository: HomePostsRepository = HomePostsRepository(context)

    init {
        view.presenter = this
    }

    override fun start() {
        loadProfile()
        loadHomePost()
    }

    override fun loadProfile() {
        /*profileRepository.getProfile(object : ProfileDataSource.LoadProfileCallback {
            override fun onProfileloaded(profile: Profile) {
                view.showProfile(profile)
            }

            override fun onFailure() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })*/
    }

    override fun loadHomePost() {
        /*homePostRepository.getHomePosts(object : HomePostsDataSource.LoadHomePostsCallback {
            override fun onHomePostsLoaded(homePosts: HomePosts) {
                view.showMaterialsList(homePosts.materials)
                view.showManufacturesList(homePosts.manufactures)
            }

            override fun onDataNotAvailable() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })*/
    }

    override fun openPostDetail(postId: Int) {
        view.showPostDetailUi(postId)
    }

    override fun setStatusBar() {
        view.setTransparentStatusBar()
    }

    override fun openHome() {
        view.showHomeUi()
    }

    override fun openMaterials() {
        view.showMaterialsUi()
    }

    override fun openManufactures() {
        view.showManufacturesUi()
    }

    override fun openBookmarks() {
        view.showBookmarksUi()
    }

    override fun openMyPage() {
        view.showMyPageUi()
    }
}