package com.bnvs.metaler.data.nickname.source

interface NicknameDataSource{
    interface ModifyNicknameCallback {
        fun onNicknameModified()
        fun onDataNotAvailable()
    }
    fun modifyNickname(callback: ModifyNicknameCallback)
}