package com.bnvs.metaler.module.viewmodel

import com.bnvs.metaler.view.myposts.ViewModelMyPosts
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myPostsViewModelModule = module {
    viewModel { ViewModelMyPosts(get(), get()) }
}