package com.bnvs.metaler.data.user.source

import com.bnvs.metaler.data.user.source.remote.UserRemoteDataSource

class UserRepository : UserDataSource{

    private val userRemoteDataSource = UserRemoteDataSource

    override fun addUser(callback: UserDataSource.AddUserCallback) {
        userRemoteDataSource.addUser(callback)
    }

    override fun deleteUser(callback: UserDataSource.DeleteUserCallback) {
        userRemoteDataSource.deleteUser(callback)
    }

    override fun checkMembership(
        kakao_id: String,
        callback: UserDataSource.CheckMembershipCallback
    ) {
        userRemoteDataSource.checkMembership(kakao_id, callback)
    }

    override fun login(callback: UserDataSource.LoginCallback) {
        userRemoteDataSource.login(callback)
    }
}