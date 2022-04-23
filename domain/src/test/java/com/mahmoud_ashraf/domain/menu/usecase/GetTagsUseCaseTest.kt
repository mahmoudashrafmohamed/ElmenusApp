package com.mahmoud_ashraf.domain.menu.usecase

import com.mahmoud_ashraf.domain.menu.models.RemoteData
import com.mahmoud_ashraf.domain.menu.models.TagsModel
import com.mahmoud_ashraf.domain.menu.repository.MenuRepository
import com.nhaarman.mockito_kotlin.any
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.Mockito
import java.net.UnknownHostException

class GetTagsUseCaseTest {

    @Test
    fun `GetTagsUseCase invoke() with page will invoke MenuRepository`() {
        // arrange
        val page = "1"
        val tags = listOf(
            TagsModel(
                "4 - Deserts",
                "https://s3-eu-west-1.amazonaws.com//elmenusv5/Normal/b2276d5d-27b7-11e8-add5-0242ac110011.jpg"
            ),
            TagsModel(
                "4 - Egyptian",
                "https://s3-eu-west-1.amazonaws.com//elmenusv5/Normal/b2276d5d-27b7-11e8-add5-0242ac110011.jpg"
            ),
        )
        val tagsData = RemoteData(tags)
        val repository = Mockito.mock(MenuRepository::class.java)
        Mockito.`when`(repository.getTags(any())).thenReturn(
            Single.just(tagsData)
        )

        val getTagsUseCase = GetTagsUseCase(repository)

        // act
        val resultObserver = getTagsUseCase(page).test()

        // assert
        Mockito.verify(repository).getTags(page)
        resultObserver.dispose()
    }

    @Test
    fun `GetTagsUseCase invoke() with page then return Single of ListOf tagsModel`() {
        // arrange
        val page = "1"
        val tags = listOf(
            TagsModel(
                "4 - Deserts",
                "https://s3-eu-west-1.amazonaws.com//elmenusv5/Normal/b2276d5d-27b7-11e8-add5-0242ac110011.jpg"
            ),
            TagsModel(
                "4 - Egyptian",
                "https://s3-eu-west-1.amazonaws.com//elmenusv5/Normal/b2276d5d-27b7-11e8-add5-0242ac110011.jpg"
            ),
        )
        val tagsData = RemoteData(tags)
        val repository = Mockito.mock(MenuRepository::class.java)
        Mockito.`when`(repository.getTags(any())).thenReturn(
            Single.just(tagsData)
        )

        val getTagsUseCase = GetTagsUseCase(repository)

        // act
        val resultObserver = getTagsUseCase(page).test()

        // assert
        resultObserver.assertValue(tagsData)
        resultObserver.dispose()
    }

    @Test
    fun `GetTagsUseCase invoke() with page then return Error`() {
        // arrange
        val page = "1"
        val exception = UnknownHostException()
        val repository = Mockito.mock(MenuRepository::class.java)
        Mockito.`when`(repository.getTags(any())).thenReturn(
            Single.error(exception)
        )
        val getTagsUseCase = GetTagsUseCase(repository)
        // act
        val resultObserver = getTagsUseCase(page).test()

        // assert
        resultObserver.assertError(exception)
        resultObserver.dispose()
    }

}
