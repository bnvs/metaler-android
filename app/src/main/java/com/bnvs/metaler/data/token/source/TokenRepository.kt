package com.bnvs.metaler.data.token.source

import android.content.Context
import com.bnvs.metaler.data.token.AccessToken
import com.bnvs.metaler.data.token.SigninToken
import com.bnvs.metaler.data.token.source.local.TokenLocalDataSource

class TokenRepository(context: Context) : TokenDataSource{

    private val tokenLocalDataSource = TokenLocalDataSource(context)

    override fun getSigninToken(callback: TokenDataSource.LoadSigninTokenCallback) {
        tokenLocalDataSource.getSigninToken(callback)
    }

    override fun saveSigninToken(token: SigninToken) {
        tokenLocalDataSource.saveSigninToken(token)
    }

    override fun getAccessToken(callback: TokenDataSource.LoadAccessTokenCallback) {
        tokenLocalDataSource.getAccessToken(callback)
    }

    override fun saveAccessToken(token: AccessToken) {
        tokenLocalDataSource.saveAccessToken(token)
    }
}