package com.bnvs.metaler.data.user.modification.source.local

import android.content.Context
import com.bnvs.metaler.data.user.certification.model.User
import com.bnvs.metaler.data.user.modification.model.Nickname
import com.bnvs.metaler.data.user.modification.model.TermsAgreements
import com.google.gson.GsonBuilder

class UserModificationLocalDataSourceImpl(context: Context) : UserModificationLocalDataSource {

    private val sharedPreferences =
        context.getSharedPreferences("LOCAL_PROFILE_DATA", Context.MODE_PRIVATE)

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
            sharedPreferences.edit()
                .putString("userInfo", GsonBuilder().create().toJson(userInfoData)).commit()
            onSuccess()
        } else {
            onFailure()
        }
    }

    override fun saveTermsAgreements(request: TermsAgreements) {
        sharedPreferences.edit()
            .putString("termsAgreements", GsonBuilder().create().toJson(request)).commit()
    }

    override fun getTermsAgreements(
        onSuccess: (agreements: TermsAgreements) -> Unit,
        onFailure: () -> Unit
    ) {
        val agreements = sharedPreferences.getString("termsAgreements", null)
        if (agreements != null) {
            onSuccess(GsonBuilder().create().fromJson(agreements, TermsAgreements::class.java))
        } else {
            onFailure()
        }
    }
}