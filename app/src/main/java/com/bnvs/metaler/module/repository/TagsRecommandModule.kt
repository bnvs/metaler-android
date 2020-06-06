package com.bnvs.metaler.module.repository

import com.bnvs.metaler.data.tags.source.remote.TagsRemoteDataSource
import com.bnvs.metaler.data.tags.source.remote.TagsRemoteDataSourceImpl
import com.bnvs.metaler.data.tags.source.repository.TagsRepository
import com.bnvs.metaler.data.tags.source.repository.TagsRepositoryImpl
import org.koin.dsl.module

val tagsRecommendModule = module {
    single<TagsRepository> { TagsRepositoryImpl(get()) }
    single<TagsRemoteDataSource> { TagsRemoteDataSourceImpl(get()) }
}