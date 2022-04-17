package com.mahmoud_ashraf.data.repository

import com.mahmoud_ashraf.data.mappers.mapToDomain
import com.mahmoud_ashraf.data.sources.remote.TagsRemoteDataSource
import com.mahmoud_ashraf.domain.menu.models.ItemOfTagModel
import com.mahmoud_ashraf.domain.menu.models.TagsModel
import com.mahmoud_ashraf.domain.menu.repository.MenuRepository
import io.reactivex.Single

class MenuRepositoryImpl(private val tagsRemoteDataSource: TagsRemoteDataSource) : MenuRepository {
    override fun getTags(page: String): Single<List<TagsModel>> {
        return tagsRemoteDataSource.getTags(page).map {  it.mapToDomain() }
    }

    override fun getItemsOfTags(tagName: String): Single<List<ItemOfTagModel>> {
        return tagsRemoteDataSource.getItemsTags(tagName).map {  it.mapToDomain() }
    }
}