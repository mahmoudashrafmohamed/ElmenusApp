package com.mahmoud_ashraf.domain.menu.usecase

import com.mahmoud_ashraf.domain.menu.repository.MenuRepository

class GetItemsOfTagsUseCase(private val menuRepository: MenuRepository) {
    operator fun invoke(tagName: String) =
        menuRepository.getItemsOfTags(tagName)
}