package com.example.metaler_android.manufactures

import com.example.metaler_android.BasePresenter
import com.example.metaler_android.BaseView

/**
 * Contract interface 는
 * View 와 Presenter 에 사용될 함수를 정의하고 관리하는데 사용
 * */

interface ContractManufactures {
    interface View : BaseView<Presenter> {
        //
    }

    interface Presenter : BasePresenter {
        //
    }
}