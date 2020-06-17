package com.bnvs.metaler.module.viewmodel

import com.bnvs.metaler.view.home.ViewModelHome
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeViewModelModule = module {
    viewModel { ViewModelHome(get(), get()) }
}