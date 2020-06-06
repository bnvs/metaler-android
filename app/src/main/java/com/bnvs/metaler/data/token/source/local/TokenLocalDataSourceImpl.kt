package com.bnvs.metaler.data.token.source.local

import android.content.Context
import com.bnvs.metaler.data.token.model.AccessToken
import com.bnvs.metaler.data.token.model.SigninToken
import com.bnvs.metaler.util.constants.ACCESS_TOKEN
import com.bnvs.metaler.util.constants.LOCAL_TOKEN_DATA
import com.bnvs.metaler.util.constants.SIGN_IN_TOKEN

class TokenLocalDataSourceImpl(context: Context) : TokenLocalDataSource {

    private val sharedPreferences =
        context.getSharedPreferences(LOCAL_TOKEN_DATA, Context.MODE_PRIVATE)

    override fun getSigninToken(
        onTokenLoaded: (token: SigninToken) -> Unit,
        onTokenNotExist: () -> Unit
    ) {
        val token = sharedPreferences.getString(SIGN_IN_TOKEN, null)
        when {
            token != null -> onTokenLoaded(SigninToken(token))
            else -> onTokenNotExist()
        }
    }

    override fun saveSigninToken(token: SigninToken) {
        sharedPreferences.edit().putString(SIGN_IN_TOKEN, token.signin_token).commit()
    }

    override fun getAccessToken(
        onTokenLoaded: (token: AccessToken) -> Unit,
        onTokenNotExist: () -> Unit
    ) {
        val token = sharedPreferences.getString(ACCESS_TOKEN, null)
        when {
            token != null -> onTokenLoaded(AccessToken(token))
            else -> onTokenNotExist()
        }
    }

    override fun saveAccessToken(token: AccessToken) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, token.access_token).commit()
    }

    override fun deleteTokens() {
        sharedPreferences.edit().clear().commit()
    }
}