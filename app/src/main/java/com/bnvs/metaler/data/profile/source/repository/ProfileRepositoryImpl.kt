package com.bnvs.metaler.data.profile.source.repository

import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.data.profile.source.local.ProfileLocalDataSource
import com.bnvs.metaler.data.user.certification.model.User

class ProfileRepositoryImpl(
    private val profileLocalDataSource: ProfileLocalDataSource
) : ProfileRepository {

    override fun getProfile(
        onProfileLoaded: (profile: Profile) -> Unit,
        onProfileNotExist: () -> Unit
    ) {
        profileLocalDataSource.getProfile(onProfileLoaded, onProfileNotExist)
    }

    override fun getUserInfo(
        onUserInfoLoaded: (user: User) -> Unit,
        onUserInfoNotExist: () -> Unit
    ) {
        profileLocalDataSource.getUserInfo(onUserInfoLoaded, onUserInfoNotExist)
    }

    override fun saveUserInfo(user: User) {
        profileLocalDataSource.saveUserInfo(user)
    }

}