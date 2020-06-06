package com.bnvs.metaler.module

import com.bnvs.metaler.data.token.source.local.TokenLocalDataSource
import com.bnvs.metaler.data.token.source.local.TokenLocalDataSourceImpl
import com.bnvs.metaler.data.token.source.repository.TokenRepository
import com.bnvs.metaler.data.token.source.repository.TokenRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val tokenModule = module {
    single<TokenRepository> { TokenRepositoryImpl(get()) }
    single<TokenLocalDataSource> { TokenLocalDataSourceImpl(androidContext()) }
}