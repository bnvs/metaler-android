package com.bnvs.metaler.data.token.source

import android.content.Context
import com.bnvs.metaler.data.token.AccessToken
import com.bnvs.metaler.data.token.SigninToken
import com.bnvs.metaler.data.token.source.local.TokenLocalDataSource

class TokenRepository(context: Context) : TokenDataSource {

    private val tokenLocalDataSource = TokenLocalDataSource(context)

    override fun getSigninToken(
        onTokenLoaded: (token: SigninToken) -> Unit,
        onTokenNotExist: () -> Unit
    ) {
        tokenLocalDataSource.getSigninToken(onTokenLoaded, onTokenNotExist)
    }

    override fun saveSigninToken(token: SigninToken) {
        tokenLocalDataSource.saveSigninToken(token)
    }

    override fun getAccessToken(
        onTokenLoaded: (token: AccessToken) -> Unit,
        onTokenNotExist: () -> Unit
    ) {
        tokenLocalDataSource.getAccessToken(onTokenLoaded, onTokenNotExist)
    }

    override fun saveAccessToken(token: AccessToken) {
        tokenLocalDataSource.saveAccessToken(token)
    }
}