package com.bnvs.metaler.module.viewmodel

import com.bnvs.metaler.view.termsagree.ViewModelTermsAgree
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val termsAgreeViewModelModule = module {
    viewModel { ViewModelTermsAgree(get()) }
}