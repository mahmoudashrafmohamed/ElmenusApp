package com.mahmoud_ashraf.data.repository

import com.mahmoud_ashraf.data.mappers.mapToDomain
import com.mahmoud_ashraf.data.mappers.mapToLocalEntity
import com.mahmoud_ashraf.data.mappers.mapToRemote
import com.mahmoud_ashraf.data.models.local.ItemOfTagLocalEntity
import com.mahmoud_ashraf.data.sources.local.TagsLocalDataSource
import com.mahmoud_ashraf.data.sources.remote.TagsRemoteDataSource
import com.mahmoud_ashraf.domain.core.loadRemoteData
import com.mahmoud_ashraf.domain.menu.models.*
import com.mahmoud_ashraf.domain.menu.repository.MenuRepository
import io.reactivex.rxjava3.core.Single

class MenuRepositoryImpl(
    private val tagsRemoteDataSource: TagsRemoteDataSource,
    private val tagsLocalDataSource: TagsLocalDataSource,
) : MenuRepository {
    override fun getTags(page: String): Single<Data<List<TagsModel>>> =
        loadRemoteData(
            remote = tagsRemoteDataSource.getTags(page).map { RemoteData(it.tags.mapToDomain()) },
            local = tagsLocalDataSource.getTags(page)
                .map { LocalData(it.mapToRemote().mapToDomain()) },
            saveData = { data -> tagsLocalDataSource.insertTags(data.mapToLocalEntity(page)) }
        )

    override fun getItemsOfTags(tagName: String): Single<Data<List<ItemOfTagModel>>> =
        loadRemoteData(
            remote = tagsRemoteDataSource.getItemsTags(tagName)
                .map { RemoteData(it.items.mapToDomain()) },
            local = tagsLocalDataSource.getItemsOfTag(tagName)
                .map { LocalData(it.mapToRemote().mapToDomain()) },
            saveData = { data -> tagsLocalDataSource.insertItemsOfTag(data.mapToLocalEntity(tagName)) }
        )

}











