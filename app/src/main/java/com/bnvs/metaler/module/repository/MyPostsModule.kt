package com.bnvs.metaler.module.repository

import com.bnvs.metaler.data.myposts.source.local.MyPostsLocalDataSource
import com.bnvs.metaler.data.myposts.source.local.MyPostsLocalDataSourceImpl
import com.bnvs.metaler.data.myposts.source.remote.MyPostsRemoteDataSource
import com.bnvs.metaler.data.myposts.source.remote.MyPostsRemoteDataSourceImpl
import com.bnvs.metaler.data.myposts.source.repository.MyPostsRepository
import com.bnvs.metaler.data.myposts.source.repository.MyPostsRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val myPostsModule = module {
    single<MyPostsRepository> { MyPostsRepositoryImpl(get(), get()) }
    single<MyPostsLocalDataSource> { MyPostsLocalDataSourceImpl(androidContext()) }
    single<MyPostsRemoteDataSource> { MyPostsRemoteDataSourceImpl(get()) }
}