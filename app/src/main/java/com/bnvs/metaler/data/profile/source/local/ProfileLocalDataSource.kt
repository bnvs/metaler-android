package com.bnvs.metaler.data.profile.source.local

import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.data.user.certification.model.User

interface ProfileLocalDataSource {

    fun getProfile(
        onProfileLoaded: (profile: Profile) -> Unit,
        onProfileNotExist: () -> Unit
    )

    fun getUserInfo(
        onUserInfoLoaded: (user: User) -> Unit,
        onUserInfoNotExist: () -> Unit
    )

    fun saveUserInfo(user: User)
}