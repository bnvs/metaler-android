package com.bnvs.metaler.module.repository

import com.bnvs.metaler.data.bookmarks.source.local.BookmarksLocalDataSource
import com.bnvs.metaler.data.bookmarks.source.local.BookmarksLocalDataSourceImpl
import com.bnvs.metaler.data.bookmarks.source.remote.BookmarksRemoteDataSource
import com.bnvs.metaler.data.bookmarks.source.remote.BookmarksRemoteDataSourceImpl
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val bookmarksModule = module {
    single<BookmarksRepository> { BookmarksRepositoryImpl(get(), get()) }
    single<BookmarksLocalDataSource> { BookmarksLocalDataSourceImpl(androidContext()) }
    single<BookmarksRemoteDataSource> { BookmarksRemoteDataSourceImpl(get()) }
}