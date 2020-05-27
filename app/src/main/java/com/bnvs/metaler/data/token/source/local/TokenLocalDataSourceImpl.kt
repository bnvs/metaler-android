package com.bnvs.metaler.data.token.source.local

import android.content.Context
import com.bnvs.metaler.data.token.model.AccessToken
import com.bnvs.metaler.data.token.model.SigninToken

class TokenLocalDataSourceImpl(context: Context) : TokenLocalDataSource {

    private val sharedPreferences =
        context.getSharedPreferences("LOCAL_TOKEN_DATA", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getSigninToken(
        onTokenLoaded: (token: SigninToken) -> Unit,
        onTokenNotExist: () -> Unit
    ) {
        val token = sharedPreferences.getString("signin_token", null)
        when {
            token != null -> onTokenLoaded(SigninToken(token))
            else -> onTokenNotExist()
        }
    }

    override fun saveSigninToken(token: SigninToken) {
        editor.putString("signin_token", token.signin_token)
        editor.commit()
    }

    override fun getAccessToken(
        onTokenLoaded: (token: AccessToken) -> Unit,
        onTokenNotExist: () -> Unit
    ) {
        val token = sharedPreferences.getString("access_token", null)
        when {
            token != null -> onTokenLoaded(AccessToken(token))
            else -> onTokenNotExist()
        }
    }

    override fun saveAccessToken(token: AccessToken) {
        editor.putString("access_token", token.access_token)
        editor.commit()
    }

    override fun deleteTokens() {
        editor.clear().commit()
    }
}