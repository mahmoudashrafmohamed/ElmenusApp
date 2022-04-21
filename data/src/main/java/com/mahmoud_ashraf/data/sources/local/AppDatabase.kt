package com.mahmoud_ashraf.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mahmoud_ashraf.data.models.local.ItemOfTagLocalEntity
import com.mahmoud_ashraf.data.models.local.TagsLocalEntity

@Database(entities = [TagsLocalEntity::class,ItemOfTagLocalEntity::class], version = 7, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tagsDao(): TagsDao
    abstract fun itemsOfTagsDao(): ItemsOfTagsDao
}