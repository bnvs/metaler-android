package com.bnvs.metaler.module.repository

import com.bnvs.metaler.data.user.certification.source.local.UserCertificationLocalDataSource
import com.bnvs.metaler.data.user.certification.source.local.UserCertificationLocalDataSourceImpl
import com.bnvs.metaler.data.user.certification.source.remote.UserCertificationRemoteDataSource
import com.bnvs.metaler.data.user.certification.source.remote.UserCertificationRemoteDataSourceImpl
import com.bnvs.metaler.data.user.certification.source.repository.UserCertificationRepository
import com.bnvs.metaler.data.user.certification.source.repository.UserCertificationRepositoryImpl
import com.bnvs.metaler.data.user.deactivation.source.remote.UserDeactivationRemoteDataSource
import com.bnvs.metaler.data.user.deactivation.source.remote.UserDeactivationRemoteDataSourceImpl
import com.bnvs.metaler.data.user.deactivation.source.repository.UserDeactivationRepository
import com.bnvs.metaler.data.user.deactivation.source.repository.UserDeactivationRepositoryImpl
import com.bnvs.metaler.data.user.modification.source.local.UserModificationLocalDataSource
import com.bnvs.metaler.data.user.modification.source.local.UserModificationLocalDataSourceImpl
import com.bnvs.metaler.data.user.modification.source.remote.UserModificationRemoteDataSource
import com.bnvs.metaler.data.user.modification.source.remote.UserModificationRemoteDataSourceImpl
import com.bnvs.metaler.data.user.modification.source.repository.UserModificationRepository
import com.bnvs.metaler.data.user.modification.source.repository.UserModificationRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val userModule = module {

    single<UserCertificationRepository> { UserCertificationRepositoryImpl(get(), get()) }
    single<UserCertificationLocalDataSource> { UserCertificationLocalDataSourceImpl(androidContext()) }
    single<UserCertificationRemoteDataSource> { UserCertificationRemoteDataSourceImpl(get()) }

    single<UserDeactivationRepository> { UserDeactivationRepositoryImpl(get()) }
    single<UserDeactivationRemoteDataSource> { UserDeactivationRemoteDataSourceImpl(get()) }

    single<UserModificationRepository> { UserModificationRepositoryImpl(get(), get()) }
    single<UserModificationLocalDataSource> { UserModificationLocalDataSourceImpl(androidContext()) }
    single<UserModificationRemoteDataSource> { UserModificationRemoteDataSourceImpl(get()) }

}