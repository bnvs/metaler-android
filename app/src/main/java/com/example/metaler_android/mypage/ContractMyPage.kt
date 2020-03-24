package com.example.metaler_android.mypage

import com.example.metaler_android.BasePresenter
import com.example.metaler_android.BaseView
import com.example.metaler_android.data.profile.Profile
import com.example.metaler_android.util.TapBarContract

interface ContractHome {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showProfile(profile: Profile)

    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadProfile()

    }
}