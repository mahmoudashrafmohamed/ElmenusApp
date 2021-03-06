package com.mahmoud_ashraf.data.sources.remote

import com.mahmoud_ashraf.data.models.remote.ItemsOfTagResponse
import com.mahmoud_ashraf.data.models.remote.TagsResponse
import io.reactivex.rxjava3.core.Single

class TagsRemoteDataSource(private val tagsApi: TagsApi) {

    fun getTags(page: String): Single<TagsResponse> {
        return tagsApi.getTags(page)
    }

    fun getItemsTags(tagName: String): Single<ItemsOfTagResponse> {
        return tagsApi.getItemsOfTags(tagName)
    }

}