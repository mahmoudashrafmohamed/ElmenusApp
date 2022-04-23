package com.mahmoud_ashraf.data.mappers

import com.mahmoud_ashraf.data.models.local.ItemOfTagLocalEntity
import com.mahmoud_ashraf.data.models.remote.Item
import com.mahmoud_ashraf.domain.menu.models.ItemOfTagModel

fun  List<Item>.mapToDomain() : List<ItemOfTagModel>{
    return this.map {
        ItemOfTagModel(it.id,it.name,it.description,it.photoUrl)
    }
}

fun  List<ItemOfTagLocalEntity>.mapToRemote()  : List<Item>{
    return this.map {
        Item(it.id,it.name,it.photoUrl,it.description)
    }
}

fun  List<ItemOfTagModel>.mapToLocalEntity(tagName: String): List<ItemOfTagLocalEntity> {
    return this.map {
        ItemOfTagLocalEntity(it.id,it.name,it.photoUrl,it.description,tagName)
    }
}