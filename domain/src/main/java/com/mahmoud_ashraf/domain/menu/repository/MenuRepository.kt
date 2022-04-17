package com.mahmoud_ashraf.domain.menu.repository

import com.mahmoud_ashraf.domain.menu.models.ItemOfTagModel
import com.mahmoud_ashraf.domain.menu.models.TagsModel
import io.reactivex.Single

interface MenuRepository {
    fun getTags(page : String): Single<List<TagsModel>>
    fun getItemsOfTags(tagName : String): Single<List<ItemOfTagModel>>
}