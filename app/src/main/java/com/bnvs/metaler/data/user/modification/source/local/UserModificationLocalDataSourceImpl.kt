package com.bnvs.metaler.data.user.modification.source.local

import android.content.Context
import com.bnvs.metaler.data.user.certification.model.User
import com.bnvs.metaler.data.user.modification.model.Nickname
import com.bnvs.metaler.data.user.modification.model.TermsAgreements
import com.bnvs.metaler.util.constants.LOCAL_USER_DATA
import com.bnvs.metaler.util.constants.TERMS_AGREEMENTS
import com.bnvs.metaler.util.constants.USER_INFO
import com.google.gson.GsonBuilder

class UserModificationLocalDataSourceImpl(context: Context) : UserModificationLocalDataSource {

    private val sharedPreferences =
        context.getSharedPreferences(LOCAL_USER_DATA, Context.MODE_PRIVATE)

    override fun modifyNickname(
        request: Nickname,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        val userInfo = sharedPreferences.getString(USER_INFO, null)
        if (userInfo != null) {
            val userInfoData = GsonBuilder().create().fromJson(userInfo, User::class.java).copy(
                profile_nickname = request.profile_nickname
            )
            sharedPreferences.edit()
                .putString(USER_INFO, GsonBuilder().create().toJson(userInfoData)).commit()
            onSuccess()
        } else {
            onFailure()
        }
    }

    override fun saveTermsAgreements(agreements: TermsAgreements) {
        sharedPreferences.edit()
            .putString(TERMS_AGREEMENTS, GsonBuilder().create().toJson(agreements)).commit()
    }

    override fun getTermsAgreements(
        onSuccess: (agreements: TermsAgreements) -> Unit,
        onFailure: () -> Unit
    ) {
        val agreements = sharedPreferences.getString(TERMS_AGREEMENTS, null)
        if (agreements != null) {
            onSuccess(GsonBuilder().create().fromJson(agreements, TermsAgreements::class.java))
        } else {
            onFailure()
        }
    }
}