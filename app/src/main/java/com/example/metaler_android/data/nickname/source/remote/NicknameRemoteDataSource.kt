package com.example.metaler_android.data.nickname.source.remote

import com.example.metaler_android.data.nickname.source.NicknameDataSource
import com.example.metaler_android.network.RetrofitClient

object NicknameRemoteDataSource : NicknameDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun modifyNickname(callback: NicknameDataSource.ModifyNicknameCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }
}
