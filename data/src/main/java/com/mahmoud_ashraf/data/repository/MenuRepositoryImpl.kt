package com.mahmoud_ashraf.data.repository

import android.util.Log
import com.mahmoud_ashraf.data.mappers.mapToDomain
import com.mahmoud_ashraf.data.mappers.mapToLocalEntity
import com.mahmoud_ashraf.data.mappers.mapToRemote
import com.mahmoud_ashraf.data.sources.local.TagsLocalDataSource
import com.mahmoud_ashraf.data.sources.remote.TagsRemoteDataSource
import com.mahmoud_ashraf.domain.menu.models.ItemOfTagModel
import com.mahmoud_ashraf.domain.menu.models.TagsModel
import com.mahmoud_ashraf.domain.menu.repository.MenuRepository
import io.reactivex.rxjava3.core.Single

class MenuRepositoryImpl(
    private val tagsRemoteDataSource: TagsRemoteDataSource,
    private val tagsLocalDataSource: TagsLocalDataSource,
) : MenuRepository {
    override fun getTags(page: String): Single<List<TagsModel>> {
        return tagsRemoteDataSource.getTags(page)
            .map { it.tags }
            .flatMap {
                tagsLocalDataSource.insertTags(it.mapToLocalEntity(page)).andThen(Single.just(it))
            }
            .onErrorResumeNext { t ->
                t.printStackTrace()
                tagsLocalDataSource.getTags(page)
                    .map {
                        it.mapToRemote()
                    }
            }
            .map {
                it.mapToDomain()
            }
    }

    override fun getItemsOfTags(tagName: String): Single<List<ItemOfTagModel>> {
        return tagsRemoteDataSource.getItemsTags(tagName)
            .map { it.items }
            .flatMap {
                tagsLocalDataSource.insertItemsOfTag(it.mapToLocalEntity(tagName)).andThen(Single.just(it))
            }
            .onErrorResumeNext { t ->
                t.printStackTrace()
                tagsLocalDataSource.getItemsOfTag(tagName)
                    .map {
                        Log.e("size",""+it.size)
                        Log.e("data++++++++",""+it.toString())
                        it.mapToRemote()
                    }
            }
            .map {
            it.mapToDomain()
            }
    }
}








