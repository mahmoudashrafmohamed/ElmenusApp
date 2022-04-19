package com.mahmoud_ashraf.domain.menu.usecase

import com.mahmoud_ashraf.domain.menu.repository.MenuRepository

class GetTagsUseCase (private val menuRepository: MenuRepository) {
    operator fun invoke(page: String) =
        menuRepository.getTags(page)
}