package com.mahmoud_ashraf.data.repository

import com.mahmoud_ashraf.data.mappers.mapToDomain
import com.mahmoud_ashraf.data.models.remote.Item
import com.mahmoud_ashraf.data.models.remote.ItemsOfTagResponse
import com.mahmoud_ashraf.data.models.remote.Tag
import com.mahmoud_ashraf.data.models.remote.TagsResponse
import com.mahmoud_ashraf.data.sources.local.TagsLocalDataSource
import com.mahmoud_ashraf.data.sources.remote.TagsRemoteDataSource
import com.mahmoud_ashraf.domain.menu.models.TagsModel
import com.nhaarman.mockito_kotlin.any
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class MenuRepositoryImplTest {

    @Test
    fun `getTags() with page will invoke tagsRemoteDataSource-getTags()`() {
        // arrange
        val page = "1"
        val remoteDataSource = Mockito.mock(TagsRemoteDataSource::class.java)
        val localDataSource = Mockito.mock(TagsLocalDataSource::class.java)
        Mockito.`when`(remoteDataSource.getTags(any())).thenReturn(
            Single.just(TagsResponse(listOf()))
        )
        val menuRepositoryImpl = MenuRepositoryImpl(remoteDataSource, localDataSource)

        // act
        val resultObserver = menuRepositoryImpl.getTags(page).test()

        // assert
        Mockito.verify(remoteDataSource).getTags(page)
        resultObserver.dispose()
    }

    @Test
    fun `getTags() with page will invoke tagsLocalDataSource-insertTags()`() {
        // arrange
        val page = "1"
        val tags = listOf<Tag>()
        val remoteDataSource = Mockito.mock(TagsRemoteDataSource::class.java)
        val localDataSource = Mockito.mock(TagsLocalDataSource::class.java)
        Mockito.`when`(remoteDataSource.getTags(any())).thenReturn(
            Single.just(TagsResponse(tags))
        )
        Mockito.`when`(localDataSource.insertTags(any())).thenReturn(
            Completable.complete()
        )
        val menuRepositoryImpl = MenuRepositoryImpl(remoteDataSource, localDataSource)

        // act
        val resultObserver = menuRepositoryImpl.getTags(page).test()

        // assert
        Mockito.verify(localDataSource).insertTags(listOf())
        resultObserver.dispose()
    }

    @Test
    fun `getTags() with page emit error then load data from localDataSource`() {
        // arrange
        val page = "1"
        val tags = listOf(Tag("www", "tag  -  Name"))
        val throwable = Throwable()
        val remoteDataSource = Mockito.mock(TagsRemoteDataSource::class.java)
        val localDataSource = Mockito.mock(TagsLocalDataSource::class.java)
        Mockito.`when`(remoteDataSource.getTags(any())).thenReturn(
            Single.error(throwable)
        )
        Mockito.`when`(localDataSource.insertTags(any())).thenReturn(
            Completable.complete()
        )
        val menuRepositoryImpl = MenuRepositoryImpl(remoteDataSource, localDataSource)

        // act
        val resultObserver = menuRepositoryImpl.getTags(page).test()

        // assert
        Mockito.verify(localDataSource).getTags(page)
        resultObserver.dispose()
    }

    @Test
    fun `mapToDomain() with List of Tag then return ListOf TagsModel`() {
        // arrange
        val tags = listOf(Tag("www", "tag  -  Name"))
        // act
        val tagsModel = tags.mapToDomain()
        // assert
        Assert.assertEquals(listOf(TagsModel( "tag  -  Name","www")),tagsModel)
    }

    @Test
    fun `getItemsOfTags() with page will invoke tagsRemoteDataSource-getItemsOfTags()`() {
        // arrange
        val tagName = "1"
        val remoteDataSource = Mockito.mock(TagsRemoteDataSource::class.java)
        val localDataSource = Mockito.mock(TagsLocalDataSource::class.java)
        Mockito.`when`(remoteDataSource.getItemsTags(any())).thenReturn(
            Single.just(ItemsOfTagResponse(listOf()))
        )
        val menuRepositoryImpl = MenuRepositoryImpl(remoteDataSource, localDataSource)

        // act
        val resultObserver = menuRepositoryImpl.getItemsOfTags(tagName).test()

        // assert
        Mockito.verify(remoteDataSource).getItemsTags(tagName)
        resultObserver.dispose()
    }

    @Test
    fun `getItemsOfTags() with page will invoke tagsLocalDataSource-insertItemsOfTags()`() {
        // arrange
        val tagName = "1"
        val items = listOf<Item>()
        val remoteDataSource = Mockito.mock(TagsRemoteDataSource::class.java)
        val localDataSource = Mockito.mock(TagsLocalDataSource::class.java)
        Mockito.`when`(remoteDataSource.getItemsTags(any())).thenReturn(
            Single.just(ItemsOfTagResponse(items))
        )
        Mockito.`when`(localDataSource.insertItemsOfTag(any())).thenReturn(
            Completable.complete()
        )
        val menuRepositoryImpl = MenuRepositoryImpl(remoteDataSource, localDataSource)

        // act
        val resultObserver = menuRepositoryImpl.getItemsOfTags(tagName).test()

        // assert
        Mockito.verify(localDataSource).insertItemsOfTag(listOf())
        resultObserver.dispose()
    }



}