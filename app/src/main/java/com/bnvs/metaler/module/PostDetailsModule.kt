package com.bnvs.metaler.module

import com.bnvs.metaler.data.postdetails.source.local.PostDetailsLocalDataSource
import com.bnvs.metaler.data.postdetails.source.local.PostDetailsLocalDataSourceImpl
import com.bnvs.metaler.data.postdetails.source.remote.PostDetailsRemoteDataSource
import com.bnvs.metaler.data.postdetails.source.remote.PostDetailsRemoteDataSourceImpl
import com.bnvs.metaler.data.postdetails.source.repository.PostDetailsRepository
import com.bnvs.metaler.data.postdetails.source.repository.PostDetailsRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val postDetailsModule = module {
    single<PostDetailsRepository> { PostDetailsRepositoryImpl(get(), get()) }
    single<PostDetailsLocalDataSource> { PostDetailsLocalDataSourceImpl(androidContext()) }
    single<PostDetailsRemoteDataSource> { PostDetailsRemoteDataSourceImpl(get()) }
}