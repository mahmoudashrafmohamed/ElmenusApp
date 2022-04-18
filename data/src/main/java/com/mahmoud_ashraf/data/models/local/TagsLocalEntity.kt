package com.mahmoud_ashraf.data.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags_table")
class TagsLocalEntity (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "tag_name") val tagName: String,
    @ColumnInfo(name = "photo_url") val photoURL: String,
    @ColumnInfo(name = "page") val page: String,
)