package com.bnvs.metaler.module

import com.bnvs.metaler.data.profile.source.local.ProfileLocalDataSource
import com.bnvs.metaler.data.profile.source.local.ProfileLocalDataSourceImpl
import com.bnvs.metaler.data.profile.source.repository.ProfileRepository
import com.bnvs.metaler.data.profile.source.repository.ProfileRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val profileModule = module {
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
    single<ProfileLocalDataSource> { ProfileLocalDataSourceImpl(androidContext()) }
}