package com.bnvs.metaler.module.viewmodel

import com.bnvs.metaler.view.jobinput.ViewModelJobInput
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val jobInputViewModelModule = module {
    viewModel { ViewModelJobInput(get(), get(), get(), get()) }
}