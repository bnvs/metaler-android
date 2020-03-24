package com.example.metaler_android.home

import android.content.Context
import com.example.metaler_android.data.homeposts.source.HomePostsRepository
import com.example.metaler_android.data.profile.source.ProfileRepository

class PresenterHome(
    private val context: Context,
    private val view: ContractHome.View) : ContractHome.Presenter {

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

    /**
     * TapBarContract.Presenter 에서 상속받은 함수
     * */
    override fun openHome() { view.showHomeUi(context) }
    override fun openMaterials() { view.showMaterialsUi(context) }
    override fun openManufactures() { view.showManufacturesUi(context) }
    override fun openBookmarks() { view.showBookmarksUi(context) }
    override fun openMyPage() { view.showMyPageUi(context) }

}