package com.mahmoud_ashraf.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahmoud_ashraf.data.models.local.TagsLocalEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface TagsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTags(tags: List<TagsLocalEntity>): Completable

    @Query("SELECT * FROM tags_table where page=:page")
    fun getTags(page : String): Single<List<TagsLocalEntity>>
}