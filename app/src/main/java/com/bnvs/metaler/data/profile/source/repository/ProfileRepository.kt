package com.bnvs.metaler.data.profile.source.repository

import android.content.Context
import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.data.profile.source.ProfileDataSource
import com.bnvs.metaler.data.profile.source.local.ProfileLocalDataSource
import com.bnvs.metaler.data.user.certification.model.User

class ProfileRepository(context: Context) : ProfileDataSource {

    private val profileLocalDataSource = ProfileLocalDataSource(context)

    override fun getProfile(
        onProfileLoaded: (profile: Profile) -> Unit,
        onProfileNotExist: () -> Unit
    ) {
        profileLocalDataSource.getProfile(onProfileLoaded, onProfileNotExist)
    }

    override fun saveProfile(profile: Profile) {
        profileLocalDataSource.saveProfile(profile)
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

    override fun modifyNickname(nickname: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        profileLocalDataSource.modifyNickname(nickname, onSuccess, onFailure)
    }

    override fun modifyJob() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}