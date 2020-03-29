package com.bnvs.metaler.data.token.source

import com.bnvs.metaler.data.token.AccessToken
import com.bnvs.metaler.data.token.SigninToken

interface TokenDataSource {

    interface LoadSigninTokenCallback {
        fun onTokenloaded(token: SigninToken)
        fun onTokenNotExist()
    }

    interface LoadAccessTokenCallback {
        fun onTokenloaded(token: AccessToken)
        fun onTokenNotExist()
    }

    fun getSigninToken(callback: LoadSigninTokenCallback)
    fun saveSigninToken(token: SigninToken)

    fun getAccessToken(callback: LoadAccessTokenCallback)
    fun saveAccessToken(token: AccessToken)

}