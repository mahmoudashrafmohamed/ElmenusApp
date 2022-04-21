package com.mahmoud_ashraf.data.di

import androidx.room.Room
import com.mahmoud_ashraf.data.sources.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, "menus_db").fallbackToDestructiveMigration().build() }

    single { get<AppDatabase>().tagsDao() }
    single { get<AppDatabase>().itemsOfTagsDao() }

}