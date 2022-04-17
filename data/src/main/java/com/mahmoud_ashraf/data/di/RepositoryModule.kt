package com.mahmoud_ashraf.data.di

import com.mahmoud_ashraf.data.repository.MenuRepositoryImpl
import com.mahmoud_ashraf.data.sources.remote.TagsRemoteDataSource
import com.mahmoud_ashraf.domain.menu.repository.MenuRepository
import org.koin.dsl.module


val repositoryModule = module {
    single {
        provideMenuRepository(
            get()
        )
    }
}

fun provideMenuRepository(remoteDataSource : TagsRemoteDataSource): MenuRepository {
    return MenuRepositoryImpl(remoteDataSource)
}