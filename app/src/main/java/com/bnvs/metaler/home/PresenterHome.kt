package com.bnvs.metaler.home

import android.content.Context
import com.bnvs.metaler.data.homeposts.source.HomePostsRepository
import com.bnvs.metaler.data.profile.source.ProfileRepository

class PresenterHome(
    context: Context,
    private val view: ContractHome.View
) : ContractHome.Presenter {

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

    override fun openPostDetail(clickedHomePostId: Int) {
        view.showPostDetailUi(clickedHomePostId)
    }

    override fun setStatusBar() {
        view.setTransparentStatusBar()
    }

}