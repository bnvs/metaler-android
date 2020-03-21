package com.example.metaler_android.home

/**
 * Contract interface 는
 * View 와 Presenter 에 사용될 함수를 정의하고 관리하는데 사용
 * */

interface ContractHome {
    interface View {
        fun showProfile()

        fun showMaterialsList()

        fun showManufacturesList()
    }

    interface Presenter {
        fun loadProfile()

        fun loadHomePost()
    }
}