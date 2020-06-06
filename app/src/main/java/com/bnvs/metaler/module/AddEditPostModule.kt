package com.bnvs.metaler.module

import com.bnvs.metaler.data.addeditpost.source.local.AddEditPostLocalDataSource
import com.bnvs.metaler.data.addeditpost.source.local.AddEditPostLocalDataSourceImpl
import com.bnvs.metaler.data.addeditpost.source.remote.AddEditPostRemoteDataSource
import com.bnvs.metaler.data.addeditpost.source.remote.AddEditPostRemoteDataSourceImpl
import com.bnvs.metaler.data.addeditpost.source.repository.AddEditPostRepository
import com.bnvs.metaler.data.addeditpost.source.repository.AddEditPostRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val addEditPostModule = module {
    single<AddEditPostRepository> { AddEditPostRepositoryImpl(get(), get()) }
    single<AddEditPostLocalDataSource> { AddEditPostLocalDataSourceImpl(androidContext()) }
    single<AddEditPostRemoteDataSource> { AddEditPostRemoteDataSourceImpl(get()) }
}