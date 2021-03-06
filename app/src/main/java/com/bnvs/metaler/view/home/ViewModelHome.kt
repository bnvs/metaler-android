package com.bnvs.metaler.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.homeposts.model.HomePosts
import com.bnvs.metaler.data.homeposts.source.repository.HomePostsRepository
import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.data.profile.source.repository.ProfileRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.base.posts.BasePostsViewModel

class ViewModelHome(
    private val profileRepository: ProfileRepository,
    private val homePostsRepository: HomePostsRepository
) : BasePostsViewModel() {

    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile> = _profile

    private val _homePosts = MutableLiveData<HomePosts>()
    val homePosts: LiveData<HomePosts> = _homePosts

    init {
        loadProfile()
        loadHomePosts()
    }

    private fun loadProfile() {
        profileRepository.getProfile(
            onProfileLoaded = { profile ->
                _profile.value = profile
            },
            onProfileNotExist = {
                _errorToastMessage.setMessage("프로필 데이터를 불러오는데 실패했습니다")
            }
        )
    }

    private fun loadHomePosts() {
        homePostsRepository.getHomePosts(
            onSuccess = { response ->
                if (response.materials.isNullOrEmpty() || response.manufactures.isNullOrEmpty()) {
                    _errorVisibility.value = true
                } else {
                    _homePosts.value = response
                }
            },
            onFailure = { e ->
                _errorVisibility.value = true
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

}