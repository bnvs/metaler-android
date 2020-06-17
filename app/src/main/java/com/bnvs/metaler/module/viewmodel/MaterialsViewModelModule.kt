package com.bnvs.metaler.module.viewmodel

import com.bnvs.metaler.view.materials.ViewModelMaterials
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val materialsViewModelModule = module {
    viewModel { ViewModelMaterials(get(), get(), get()) }
}