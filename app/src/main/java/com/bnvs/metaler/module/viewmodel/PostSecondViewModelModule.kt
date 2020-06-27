package com.bnvs.metaler.module.viewmodel

import com.bnvs.metaler.view.addeditpost.postsecond.ViewModelPostSecond
import com.bnvs.metaler.view.addeditpost.postsecond.tagsuggest.ViewModelTagSuggest
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postSecondViewModelModule = module {
    viewModel { ViewModelPostSecond(get()) }
    viewModel {
        ViewModelTagSuggest(
            get()
        )
    }
}