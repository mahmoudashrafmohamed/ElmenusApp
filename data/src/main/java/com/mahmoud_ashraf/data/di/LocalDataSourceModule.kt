package com.mahmoud_ashraf.data.di

import com.mahmoud_ashraf.data.sources.local.TagsDao
import com.mahmoud_ashraf.data.sources.local.TagsLocalDataSource
import org.koin.dsl.module


val localDataSourceModule = module {
    single {
        provideLocalDataSource(
            get()
        )
    }
}

fun provideLocalDataSource(tagsDao: TagsDao): TagsLocalDataSource {
    return TagsLocalDataSource(tagsDao)
}