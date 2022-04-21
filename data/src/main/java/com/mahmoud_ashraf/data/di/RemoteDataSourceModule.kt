package com.mahmoud_ashraf.data.di

import com.mahmoud_ashraf.data.repository.MenuRepositoryImpl
import com.mahmoud_ashraf.data.sources.remote.TagsApi
import com.mahmoud_ashraf.data.sources.remote.TagsRemoteDataSource
import com.mahmoud_ashraf.domain.menu.repository.MenuRepository
import org.koin.dsl.module


val remoteDataSourceModule = module {
    single {
        provideRemoteDataSource(
            get()
        )
    }
}

fun provideRemoteDataSource(tagsApi: TagsApi): TagsRemoteDataSource {
    return TagsRemoteDataSource(tagsApi)
}