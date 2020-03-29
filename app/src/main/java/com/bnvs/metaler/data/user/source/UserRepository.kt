package com.bnvs.metaler.data.user.source

import com.bnvs.metaler.data.user.CheckMembershipRequest
import com.bnvs.metaler.data.user.LoginRequest
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
        checkMembershipRequest: CheckMembershipRequest,
        callback: UserDataSource.CheckMembershipCallback
    ) {
        userRemoteDataSource.checkMembership(checkMembershipRequest, callback)
    }

    override fun login(
        loginRequest: LoginRequest,
        callback: UserDataSource.LoginCallback
    ) {
        userRemoteDataSource.login(loginRequest, callback)
    }
}