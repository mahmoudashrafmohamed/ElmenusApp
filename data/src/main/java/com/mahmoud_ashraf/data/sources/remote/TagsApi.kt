package com.mahmoud_ashraf.data.sources.remote

import com.mahmoud_ashraf.data.models.remote.ItemsOfTagResponse
import com.mahmoud_ashraf.data.models.remote.TagsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TagsApi {
    @GET("tags/{page}")
    fun getTags(
        @Path("page") page: String
    ): Single<TagsResponse>

    @GET("items/{tag_name}")
    fun getItemsOfTags(
        @Path("tag_name") tagName: String
    ): Single<ItemsOfTagResponse>

}