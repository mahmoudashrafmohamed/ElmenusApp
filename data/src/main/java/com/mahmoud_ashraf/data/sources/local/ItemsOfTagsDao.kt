package com.mahmoud_ashraf.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahmoud_ashraf.data.models.local.ItemOfTagLocalEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ItemsOfTagsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemsOfTag(tags: List<ItemOfTagLocalEntity>): Completable

    @Query("SELECT * FROM item_of_tag_table where tag_name=:tagName")
    fun getItemsOfTag(tagName : String): Single<List<ItemOfTagLocalEntity>>
}