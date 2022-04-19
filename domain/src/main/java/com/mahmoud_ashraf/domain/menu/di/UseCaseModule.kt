package com.mahmoud_ashraf.domain.menu.di

import com.mahmoud_ashraf.domain.menu.repository.MenuRepository
import com.mahmoud_ashraf.domain.menu.usecase.GetItemsOfTagsUseCase
import com.mahmoud_ashraf.domain.menu.usecase.GetTagsUseCase
import org.koin.dsl.module


val usecaseModule = module {

    single {
        provideGetTagsUseCase(
            get()
        )
    }
    single {
        provideGetItemsOfTagsUseCase(
            get()
        )
    }


}

fun provideGetTagsUseCase(menuRepository: MenuRepository): GetTagsUseCase {
    return GetTagsUseCase(menuRepository)
}

fun provideGetItemsOfTagsUseCase(menuRepository: MenuRepository): GetItemsOfTagsUseCase {
    return GetItemsOfTagsUseCase(menuRepository)
}