package com.example.metaler_android.home

import android.content.Context
import com.example.metaler_android.data.homepost.source.HomePostRepository
import com.example.metaler_android.data.profile.source.ProfileRepository

class PresenterHome(context: Context, val view: ContractHome.View) : ContractHome.Presenter {

    private val profileRepository: ProfileRepository = ProfileRepository(context)
    private val homePostRepository: HomePostRepository = HomePostRepository(context)

    init {
        view.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadProfile() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadHomePost() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}