package com.example.metaler_android.home

import com.example.metaler_android.BasePresenter
import com.example.metaler_android.BaseView

/**
 * Contract interface 는
 * View 와 Presenter 에 사용될 함수를 정의하고 관리하는데 사용
 * */

interface ContractHome {
    interface View : BaseView<Presenter> {
        fun showProfile()

        fun showMaterialsList()

        fun showManufacturesList()
    }

    interface Presenter : BasePresenter {
        fun loadProfile()

        fun loadHomePost()
    }
}