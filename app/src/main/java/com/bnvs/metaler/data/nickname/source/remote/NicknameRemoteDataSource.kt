package com.bnvs.metaler.data.nickname.source.remote

import com.bnvs.metaler.data.nickname.source.NicknameDataSource
import com.bnvs.metaler.network.RetrofitClient

object NicknameRemoteDataSource : NicknameDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun modifyNickname(callback: NicknameDataSource.ModifyNicknameCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }
}
