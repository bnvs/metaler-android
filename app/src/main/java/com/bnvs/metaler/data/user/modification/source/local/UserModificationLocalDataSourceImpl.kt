package com.bnvs.metaler.data.user.modification.source.local

import android.content.Context
import com.bnvs.metaler.data.user.certification.model.User
import com.bnvs.metaler.data.user.modification.model.Nickname
import com.google.gson.GsonBuilder

class UserModificationLocalDataSourceImpl(context: Context) : UserModificationLocalDataSource {

    private val sharedPreferences =
        context.getSharedPreferences("LOCAL_PROFILE_DATA", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun modifyNickname(
        request: Nickname,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        val userInfo = sharedPreferences.getString("userInfo", null)
        if (userInfo != null) {
            val userInfoData = GsonBuilder().create().fromJson(userInfo, User::class.java).copy(
                profile_nickname = request.profile_nickname
            )
            editor.putString("userInfo", GsonBuilder().create().toJson(userInfoData))
            editor.commit()
            onSuccess()
        } else {
            onFailure()
        }
    }
}