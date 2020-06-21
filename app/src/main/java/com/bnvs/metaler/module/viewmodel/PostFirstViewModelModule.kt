package com.bnvs.metaler.module.viewmodel

import com.bnvs.metaler.view.postfirst.ViewModelPostFirst
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postFirstViewModelModule = module {
    viewModel { ViewModelPostFirst(get(), get(), get()) }
}