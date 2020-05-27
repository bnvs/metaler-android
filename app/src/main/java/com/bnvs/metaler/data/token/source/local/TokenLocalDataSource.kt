package com.bnvs.metaler.data.token.source.local

import com.bnvs.metaler.data.token.model.AccessToken
import com.bnvs.metaler.data.token.model.SigninToken

interface TokenLocalDataSource {

    fun getSigninToken(
        onTokenLoaded: (token: SigninToken) -> Unit,
        onTokenNotExist: () -> Unit
    )

    fun saveSigninToken(token: SigninToken)

    fun getAccessToken(
        onTokenLoaded: (token: AccessToken) -> Unit,
        onTokenNotExist: () -> Unit
    )

    fun saveAccessToken(token: AccessToken)

    fun deleteTokens()

}