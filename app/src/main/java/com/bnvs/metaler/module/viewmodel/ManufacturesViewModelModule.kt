package com.bnvs.metaler.module.viewmodel

import com.bnvs.metaler.view.posts.manufactures.ViewModelManufactures
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val manufacturesViewModelModule = module {
    viewModel {
        ViewModelManufactures(get(), get(), get())
    }
}