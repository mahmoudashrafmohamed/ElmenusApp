package com.mahmoud_ashraf.data.di

import com.mahmoud_ashraf.data.sources.local.ItemsOfTagsDao
import com.mahmoud_ashraf.data.sources.local.TagsDao
import com.mahmoud_ashraf.data.sources.local.TagsLocalDataSource
import org.koin.dsl.module


val localDataSourceModule = module {
    single {
        provideLocalDataSource(
            get(),
            get()
        )
    }
}

fun provideLocalDataSource(tagsDao: TagsDao,itemsOfTagsDao: ItemsOfTagsDao): TagsLocalDataSource {
    return TagsLocalDataSource(tagsDao,itemsOfTagsDao)
}