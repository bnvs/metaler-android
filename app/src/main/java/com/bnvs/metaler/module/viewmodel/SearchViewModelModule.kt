package com.bnvs.metaler.module.viewmodel

import com.bnvs.metaler.view.search.ViewModelSearch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchViewModelModule = module {
    viewModel { ViewModelSearch(get(), get(), get()) }
}