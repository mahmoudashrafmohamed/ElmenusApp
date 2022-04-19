package com.mahmoud_ashraf.data.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_of_tag_table")
class ItemOfTagLocalEntity (
    @ColumnInfo(name = "id") val id: Long=0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "photo_url") val photoUrl: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "tag_name") val tagName: String,
    /*
    this used in room only because id return repeated in sandbox
     */
    @ColumnInfo(name = "local_id")  @PrimaryKey(autoGenerate = true) val localId: Long=0,
)