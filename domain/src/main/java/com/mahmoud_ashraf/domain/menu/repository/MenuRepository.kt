package com.mahmoud_ashraf.domain.menu.repository

import com.mahmoud_ashraf.domain.menu.models.Data
import com.mahmoud_ashraf.domain.menu.models.ItemOfTagModel
import com.mahmoud_ashraf.domain.menu.models.TagsModel
import io.reactivex.rxjava3.core.Single

interface MenuRepository {
    fun getTags(page : String):  Single<Data<List<TagsModel>>>
    fun getItemsOfTags(tagName : String): Single<Data<List<ItemOfTagModel>>>
}