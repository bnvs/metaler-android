package com.bnvs.metaler.module.viewmodel

import com.bnvs.metaler.view.mypage.ViewModelMyPage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myPageViewModelModule = module {
    viewModel { ViewModelMyPage(get(), get(), get(), get(), get()) }
}