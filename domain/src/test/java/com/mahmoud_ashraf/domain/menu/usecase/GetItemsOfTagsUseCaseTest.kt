package com.mahmoud_ashraf.domain.menu.usecase

import com.mahmoud_ashraf.domain.menu.models.ItemOfTagModel
import com.mahmoud_ashraf.domain.menu.repository.MenuRepository
import com.nhaarman.mockito_kotlin.any
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito
import java.net.UnknownHostException

class GetItemsOfTagsUseCaseTest {

    @Test
    fun `GetItemsOfTagsUseCase invoke() with tagName will invoke MenuRepository`() {
        // arrange
        val tagName = "4 - Deserts"
        val items = listOf(
            ItemOfTagModel(
                4,
                "desert",
                "good",
                "https://s3-eu-west-1.amazonaws.com//elmenusv5/Normal/b2276d5d-27b7-11e8-add5-0242ac110011.jpg",
            ),
            ItemOfTagModel(
                4,
                "desert",
                "good",
                "https://s3-eu-west-1.amazonaws.com//elmenusv5/Normal/b2276d5d-27b7-11e8-add5-0242ac110011.jpg",
            ),

        )
        val repository = Mockito.mock(MenuRepository::class.java)
        Mockito.`when`(repository.getItemsOfTags(any())).thenReturn(
            Single.just(items)
        )

        val getItemsOfTagsUseCase = GetItemsOfTagsUseCase(repository)

        // act
        val resultObserver = getItemsOfTagsUseCase(tagName).test()

        // assert
        Mockito.verify(repository).getItemsOfTags(tagName)
        resultObserver.dispose()
    }
    @Test
    fun `GetItemsOfTagsUseCase invoke() with tagName then return Single of ListOf ItemsOfTagModel`() {
        // arrange
        val tagName = "4 - Deserts"
        val items = listOf(
            ItemOfTagModel(
                4,
                "desert",
                "good",
                "https://s3-eu-west-1.amazonaws.com//elmenusv5/Normal/b2276d5d-27b7-11e8-add5-0242ac110011.jpg",
            ),
            ItemOfTagModel(
                4,
                "desert",
                "good",
                "https://s3-eu-west-1.amazonaws.com//elmenusv5/Normal/b2276d5d-27b7-11e8-add5-0242ac110011.jpg",
            ),

        )
        val repository = Mockito.mock(MenuRepository::class.java)
        Mockito.`when`(repository.getItemsOfTags(any())).thenReturn(
            Single.just(items)
        )

        val getItemsOfTagsUseCase = GetItemsOfTagsUseCase(repository)

        // act
        val resultObserver = getItemsOfTagsUseCase(tagName).test()

        // assert
        resultObserver.assertValue(items)
        resultObserver.dispose()
    }

    @Test
    fun `GetItemsOfTagsUseCase invoke() with tagName then return Error`() {
        // arrange
        val tagName = "4 - Deserts"
        val exception = UnknownHostException()
        val repository = Mockito.mock(MenuRepository::class.java)
        Mockito.`when`(repository.getItemsOfTags(any())).thenReturn(
            Single.error(exception)
        )

        val getItemsOfTagsUseCase = GetItemsOfTagsUseCase(repository)

        // act
        val resultObserver = getItemsOfTagsUseCase(tagName).test()

        // assert
        resultObserver.assertError(exception)
        resultObserver.dispose()
    }

}