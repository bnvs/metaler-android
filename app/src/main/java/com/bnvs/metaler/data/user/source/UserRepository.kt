package com.bnvs.metaler.data.user.source

import com.bnvs.metaler.data.user.AddUserRequest
import com.bnvs.metaler.data.user.CheckMembershipRequest
import com.bnvs.metaler.data.user.LoginRequest
import com.bnvs.metaler.data.user.source.remote.UserRemoteDataSource

class UserRepository : UserDataSource{

    private val userRemoteDataSource = UserRemoteDataSource

    override fun addUser(
        request: AddUserRequest,
        callback: UserDataSource.AddUserCallback
    ) {
        userRemoteDataSource.addUser(request, callback)
    }

    override fun deleteUser(callback: UserDataSource.DeleteUserCallback) {
        userRemoteDataSource.deleteUser(callback)
    }

    override fun checkMembership(
        request: CheckMembershipRequest,
        callback: UserDataSource.CheckMembershipCallback
    ) {
        userRemoteDataSource.checkMembership(request, callback)
    }

    override fun login(
        request: LoginRequest,
        callback: UserDataSource.LoginCallback
    ) {
        userRemoteDataSource.login(request, callback)
    }
}