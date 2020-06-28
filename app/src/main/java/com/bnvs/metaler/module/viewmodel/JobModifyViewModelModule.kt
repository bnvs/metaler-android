package com.bnvs.metaler.module.viewmodel

import com.bnvs.metaler.view.jobmodify.ViewModelJobModify
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val jobModifyViewModelModule = module {
    viewModel { ViewModelJobModify(get()) }
}