package com.mahmoud_ashraf.data.sources.local

import com.mahmoud_ashraf.data.models.local.TagsLocalEntity
import com.mahmoud_ashraf.data.models.remote.ItemsOfTagResponse
import com.mahmoud_ashraf.data.models.remote.TagsResponse
import io.reactivex.Completable
import io.reactivex.Single

class TagsLocalDataSource(private val tagsDao: TagsDao) {

    fun getTags(page: String): Single<List<TagsLocalEntity>> {
        return tagsDao.getTags(page)
    }

    fun insertTags(tags : List<TagsLocalEntity>): Completable {
        return tagsDao.insertTags(tags)
    }



}