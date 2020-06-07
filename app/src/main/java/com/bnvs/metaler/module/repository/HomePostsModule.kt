package com.bnvs.metaler.module.repository

import com.bnvs.metaler.data.homeposts.source.local.HomePostsLocalDataSource
import com.bnvs.metaler.data.homeposts.source.local.HomePostsLocalDataSourceImpl
import com.bnvs.metaler.data.homeposts.source.remote.HomePostsRemoteDataSource
import com.bnvs.metaler.data.homeposts.source.remote.HomePostsRemoteDataSourceImpl
import com.bnvs.metaler.data.homeposts.source.repository.HomePostsRepository
import com.bnvs.metaler.data.homeposts.source.repository.HomePostsRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val homePostsModule = module {
    single<HomePostsRepository> { HomePostsRepositoryImpl(get(), get()) }
    single<HomePostsLocalDataSource> { HomePostsLocalDataSourceImpl(androidContext()) }
    single<HomePostsRemoteDataSource> { HomePostsRemoteDataSourceImpl(get()) }
}