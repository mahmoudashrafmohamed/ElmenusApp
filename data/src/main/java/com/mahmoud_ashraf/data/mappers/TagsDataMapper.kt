package com.mahmoud_ashraf.data.mappers

import com.mahmoud_ashraf.data.models.remote.ItemsOfTagResponse
import com.mahmoud_ashraf.data.models.remote.TagsResponse
import com.mahmoud_ashraf.domain.menu.models.ItemOfTagModel
import com.mahmoud_ashraf.domain.menu.models.TagsModel

fun TagsResponse.mapToDomain() = this.tags.map {
    TagsModel(it.tagName,it.photoURL)
}
fun ItemsOfTagResponse.mapToDomain() = this.items.map {
    ItemOfTagModel(it.id,it.name,it.description,it.photoUrl)
}