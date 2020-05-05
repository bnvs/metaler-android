package com.bnvs.metaler.data.profile.source.local

import android.content.Context
import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.data.profile.source.ProfileDataSource
import com.bnvs.metaler.data.user.certification.model.User
import com.google.gson.GsonBuilder

class ProfileLocalDataSource(context: Context) : ProfileDataSource {

    private val sharedPreferences =
        context.getSharedPreferences("LOCAL_PROFILE_DATA", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getProfile(
        onProfileLoaded: (profile: Profile) -> Unit,
        onProfileNotExist: () -> Unit
    ) {
        val profile = sharedPreferences.getString("profile", null)
        if (profile != null) {
            onProfileLoaded(GsonBuilder().create().fromJson(profile, Profile::class.java))
        } else {
            onProfileNotExist()
        }
    }

    override fun saveProfile(profile: Profile) {
        editor.putString("profile", GsonBuilder().create().toJson(profile))
        editor.commit()
    }

    override fun getUserInfo(
        onUserInfoLoaded: (user: User) -> Unit,
        onUserInfoNotExist: () -> Unit
    ) {
        val userInfo = sharedPreferences.getString("userInfo", null)
        if (userInfo != null) {
            onUserInfoLoaded(GsonBuilder().create().fromJson(userInfo, User::class.java))
        } else {
            onUserInfoNotExist()
        }
    }

    override fun saveUserInfo(user: User) {
        editor.putString("userInfo", GsonBuilder().create().toJson(user))
        editor.commit()
    }

    override fun modifyNickname(
        nickname: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        val profile = sharedPreferences.getString("profile", null)
        val userInfo = sharedPreferences.getString("userInfo", null)
        if (profile != null && userInfo != null) {
            val profileData = GsonBuilder().create().fromJson(profile, Profile::class.java)
            val modifiedProfileData =
                Profile(nickname, profileData.profile_image_url, profileData.profile_email)
            editor.putString("profile", GsonBuilder().create().toJson(modifiedProfileData))
            editor.commit()

            val userInfoData = GsonBuilder().create().fromJson(userInfo, User::class.java)
            userInfoData.profile_nickname = nickname
            editor.putString("userInfo", GsonBuilder().create().toJson(userInfoData))
            editor.commit()

            onSuccess()
        } else {
            onFailure()
        }
    }

    override fun modifyJob() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}