package com.mahmoud_ashraf.data.mappers

import com.mahmoud_ashraf.data.models.local.TagsLocalEntity
import com.mahmoud_ashraf.data.models.remote.ItemsOfTagResponse
import com.mahmoud_ashraf.data.models.remote.Tag
import com.mahmoud_ashraf.data.models.remote.TagsResponse
import com.mahmoud_ashraf.domain.menu.models.ItemOfTagModel
import com.mahmoud_ashraf.domain.menu.models.TagsModel

fun  List<Tag>.mapToDomain()  = this.map {
    TagsModel(it.tagName,it.photoURL)
}

fun List<TagsModel>.mapToLocalEntity(page: String): List<TagsLocalEntity> {
    return this.map {
        TagsLocalEntity(tagName = it.tagName, photoURL = it.photoURL, page = page)
    }
}


 fun  List<TagsLocalEntity>.mapToRemote() : List<Tag> {
    return this.map {
        Tag(tagName = it.tagName, photoURL = it.photoURL)
    }
}