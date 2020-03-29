package com.bnvs.metaler.data.token.source.local

import android.content.Context
import com.bnvs.metaler.data.token.AccessToken
import com.bnvs.metaler.data.token.SigninToken
import com.bnvs.metaler.data.token.source.TokenDataSource

class TokenLocalDataSource (context: Context) : TokenDataSource {

    private val sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getSigninToken(callback: TokenDataSource.LoadSigninTokenCallback) {
        var token = sharedPreferences.getString("signin_token", null)
        if (token != null) {
            callback.onTokenloaded(SigninToken(token))
        }else {
            callback.onTokenNotExist()
        }
    }

    override fun saveSigninToken(token: SigninToken) {
        editor.putString("signin_token", token.signin_token)
        editor.commit()
    }

    override fun getAccessToken(callback: TokenDataSource.LoadAccessTokenCallback) {
        var token = sharedPreferences.getString("access_token", null)
        var validTime = sharedPreferences.getString("access_token_valid_time", null)
        if (token != null) {
            callback.onTokenloaded(AccessToken(token, validTime!!))
        }else {
            callback.onTokenNotExist()
        }
    }

    override fun saveAccessToken(token: AccessToken) {
        editor.putString("access_token", token.access_token)
        editor.putString("access_token_valid_time", token.valid_time)
        editor.commit()
    }
}