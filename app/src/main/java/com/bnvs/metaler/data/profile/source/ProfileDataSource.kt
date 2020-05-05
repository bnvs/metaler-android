package com.bnvs.metaler.data.profile.source

import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.data.user.certification.model.User

interface ProfileDataSource {

    fun getProfile(
        onProfileLoaded: (profile: Profile) -> Unit,
        onProfileNotExist: () -> Unit
    )

    fun saveProfile(profile: Profile)

    fun getUserInfo(
        onUserInfoLoaded: (user: User) -> Unit,
        onUserInfoNotExist: () -> Unit
    )

    fun saveUserInfo(user: User)

    fun modifyNickname(
        nickname: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

    fun modifyJob()

}