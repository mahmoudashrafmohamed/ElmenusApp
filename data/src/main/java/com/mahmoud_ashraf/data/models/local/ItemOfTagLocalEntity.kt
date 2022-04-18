package com.mahmoud_ashraf.data.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "item_of_tag_table")
class ItemOfTagLocalEntity (
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "photo_url") val photoUrl: String,
    @ColumnInfo(name = "description") val description: String,
)