package com.bnvs.metaler.module.repository

import com.bnvs.metaler.data.posts.source.local.PostsLocalDataSource
import com.bnvs.metaler.data.posts.source.local.PostsLocalDataSourceImpl
import com.bnvs.metaler.data.posts.source.remote.PostsRemoteDataSource
import com.bnvs.metaler.data.posts.source.remote.PostsRemoteDataSourceImpl
import com.bnvs.metaler.data.posts.source.repository.PostsRepository
import com.bnvs.metaler.data.posts.source.repository.PostsRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val postsModule = module {
    single<PostsRepository> { PostsRepositoryImpl(get(), get()) }
    single<PostsLocalDataSource> { PostsLocalDataSourceImpl(androidContext()) }
    single<PostsRemoteDataSource> { PostsRemoteDataSourceImpl(get()) }
}