package com.bnvs.metaler.module.viewmodel

import com.bnvs.metaler.view.bookmarks.ViewModelBookmarks
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val bookmarksViewModelModule = module {
    viewModel { ViewModelBookmarks(get()) }
}