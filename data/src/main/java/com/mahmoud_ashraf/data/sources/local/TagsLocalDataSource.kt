package com.mahmoud_ashraf.data.sources.local

import com.mahmoud_ashraf.data.models.local.ItemOfTagLocalEntity
import com.mahmoud_ashraf.data.models.local.TagsLocalEntity
import io.reactivex.Completable
import io.reactivex.Single

class TagsLocalDataSource(private val tagsDao: TagsDao,
                          private val itemsOfTagsDao: ItemsOfTagsDao) {

    fun getTags(page: String): Single<List<TagsLocalEntity>> {
        return tagsDao.getTags(page)
    }

    fun insertTags(tags : List<TagsLocalEntity>): Completable {
        return tagsDao.insertTags(tags)
    }

    fun getItemsOfTag(tagName : String): Single<List<ItemOfTagLocalEntity>>{
        return itemsOfTagsDao.getItemsOfTag(tagName)
    }

    fun insertItemsOfTag(tags: List<ItemOfTagLocalEntity>): Completable{
        return itemsOfTagsDao.insertItemsOfTag(tags)
    }

}