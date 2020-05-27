package com.bnvs.metaler.data.token.source.repository

import com.bnvs.metaler.data.token.model.AccessToken
import com.bnvs.metaler.data.token.model.SigninToken
import com.bnvs.metaler.data.token.source.local.TokenLocalDataSource

class TokenRepositoryImpl(
    private val tokenLocalDataSource: TokenLocalDataSource
) : TokenRepository {

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

    override fun deleteTokens() {
        tokenLocalDataSource.deleteTokens()
    }
}