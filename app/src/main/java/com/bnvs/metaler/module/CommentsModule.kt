package com.bnvs.metaler.module

import com.bnvs.metaler.data.comments.source.local.CommentsLocalDataSource
import com.bnvs.metaler.data.comments.source.local.CommentsLocalDataSourceImpl
import com.bnvs.metaler.data.comments.source.remote.CommentsRemoteDataSource
import com.bnvs.metaler.data.comments.source.remote.CommentsRemoteDataSourceImpl
import com.bnvs.metaler.data.comments.source.repository.CommentsRepository
import com.bnvs.metaler.data.comments.source.repository.CommentsRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val commentsModule = module {
    single<CommentsRepository> { CommentsRepositoryImpl(get(), get()) }
    single<CommentsLocalDataSource> { CommentsLocalDataSourceImpl(androidContext()) }
    single<CommentsRemoteDataSource> { CommentsRemoteDataSourceImpl(get()) }
}