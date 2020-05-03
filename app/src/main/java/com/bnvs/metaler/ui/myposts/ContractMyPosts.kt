package com.bnvs.metaler.ui.myposts

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.util.TapBarContract

interface ContractMyPosts {
    interface View : BaseView<Presenter>, TapBarContract.View {
    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
    }
}