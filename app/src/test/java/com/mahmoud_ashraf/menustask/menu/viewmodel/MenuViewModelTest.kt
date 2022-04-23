package com.mahmoud_ashraf.menustask.menu.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mahmoud_ashraf.domain.menu.models.Data
import com.mahmoud_ashraf.domain.menu.models.ItemOfTagModel
import com.mahmoud_ashraf.domain.menu.models.RemoteData
import com.mahmoud_ashraf.domain.menu.models.TagsModel
import com.mahmoud_ashraf.domain.menu.usecase.GetItemsOfTagsUseCase
import com.mahmoud_ashraf.domain.menu.usecase.GetTagsUseCase
import com.mahmoud_ashraf.menustask.core.exceptions.MenusException
import com.nhaarman.mockito_kotlin.any
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MenuViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `getTags() with page then emit LoadingState`() {
        // arrange
        val page = "1"
        val getTagsUseCase = Mockito.mock(GetTagsUseCase::class.java)
        val getItemsOfTagsUseCase = Mockito.mock(GetItemsOfTagsUseCase::class.java)
        Mockito.`when`(getTagsUseCase.invoke(any())).thenReturn(
            Single.never()
        )
        val viewModel = MenuViewModel(
            getTagsUseCase=getTagsUseCase,
            getItemsOfTagsUseCase = getItemsOfTagsUseCase,
            backgroundScheduler = Schedulers.trampoline()
        )
        // act
        viewModel.getTags(page = page)
        //assert
        Assert.assertEquals(MenuScreenStates.FirstLoading, viewModel.screenState.value)
    }

    @Test
    fun `getTags() with page then emit SuccessState With results`() {
        // arrange
        val tags = listOf(TagsModel("tag1","https:www.img.com"))
        val itemsOfTags = listOf(ItemOfTagModel(1,"tag1","https:www.img.com",""))
        val page = "1"
        val getTagsUseCase = Mockito.mock(GetTagsUseCase::class.java)
        val getItemsOfTagsUseCase = Mockito.mock(GetItemsOfTagsUseCase::class.java)
        Mockito.`when`(getTagsUseCase.invoke(any())).thenReturn(
            Single.just(RemoteData(tags))
        )
        val viewModel = MenuViewModel(
            getTagsUseCase=getTagsUseCase,
            getItemsOfTagsUseCase = getItemsOfTagsUseCase,
            backgroundScheduler = Schedulers.trampoline()
        )
        // act
        viewModel.getTags(page = page)
        //assert
        Assert.assertEquals(MenuScreenStates.TagsLoadedSuccessfully(tags), viewModel.screenState.value)
    }

    @Test
    fun `getTags() with page then emit ErrorState With error`() {
        // arrange
        val tags = listOf(TagsModel("tag1","https:www.img.com"))
        val page = "1"
        val throwable = MenusException.ServerDown
        val getTagsUseCase = Mockito.mock(GetTagsUseCase::class.java)
        val getItemsOfTagsUseCase = Mockito.mock(GetItemsOfTagsUseCase::class.java)
        Mockito.`when`(getTagsUseCase.invoke(any())).thenReturn(
            Single.error(throwable)
        )
        val viewModel = MenuViewModel(
            getTagsUseCase=getTagsUseCase,
            getItemsOfTagsUseCase = getItemsOfTagsUseCase,
            backgroundScheduler = Schedulers.trampoline()
        )
        // act
        viewModel.getTags(page = page)
        //assert
        Assert.assertEquals(MenuScreenStates.Error(throwable), viewModel.screenState.value)
    }

    @Test
    fun `getItemsOfTag() with tagName then emit LoadingState`() {
        // arrange
        val tagName = "1"
        val getTagsUseCase = Mockito.mock(GetTagsUseCase::class.java)
        val getItemsOfTagsUseCase = Mockito.mock(GetItemsOfTagsUseCase::class.java)
        Mockito.`when`(getTagsUseCase.invoke(any())).thenReturn(
            Single.just(RemoteData(listOf()))
        )
        Mockito.`when`(getItemsOfTagsUseCase.invoke(any())).thenReturn(
            Single.never()
        )
        val viewModel = MenuViewModel(
            getTagsUseCase=getTagsUseCase,
            getItemsOfTagsUseCase = getItemsOfTagsUseCase,
            backgroundScheduler = Schedulers.trampoline()
        )
        // act
        viewModel.getItemsOfTags(tagName)
        //assert
        Assert.assertEquals(MenuScreenStates.Loading, viewModel.screenState.value)
    }

    @Test
    fun `getItemsOfTag() with tagName then emit SuccessState With results`() {
        // arrange
        val itemsOfTags = listOf(ItemOfTagModel(1,"tag1","https:www.img.com",""))
        val tagName = "1"
        val getTagsUseCase = Mockito.mock(GetTagsUseCase::class.java)
        val getItemsOfTagsUseCase = Mockito.mock(GetItemsOfTagsUseCase::class.java)
        Mockito.`when`(getTagsUseCase.invoke(any())).thenReturn(
            Single.just(RemoteData(listOf()))
        )
        Mockito.`when`(getItemsOfTagsUseCase.invoke(any())).thenReturn(
            Single.just(RemoteData(itemsOfTags))
        )
        val viewModel = MenuViewModel(
            getTagsUseCase=getTagsUseCase,
            getItemsOfTagsUseCase = getItemsOfTagsUseCase,
            backgroundScheduler = Schedulers.trampoline()
        )
        // act
        viewModel.getItemsOfTags(tagName)
        //assert
        Assert.assertEquals(MenuScreenStates.ItemsOfTagLoadedSuccessfully(itemsOfTags), viewModel.screenState.value)
    }

    @Test
    fun `getItemsOfTag() with tagName then emit ErrorState With error`() {
        // arrange
        val itemsOfTags = listOf(ItemOfTagModel(1,"tag1","https:www.img.com",""))
        val tagName = "1"
        val throwable = MenusException.ServerDown
        val getTagsUseCase = Mockito.mock(GetTagsUseCase::class.java)
        val getItemsOfTagsUseCase = Mockito.mock(GetItemsOfTagsUseCase::class.java)
        Mockito.`when`(getTagsUseCase.invoke(any())).thenReturn(
            Single.just(RemoteData(listOf()))
        )
        Mockito.`when`(getItemsOfTagsUseCase.invoke(any())).thenReturn(
            Single.error(throwable)
        )
        val viewModel = MenuViewModel(
            getTagsUseCase=getTagsUseCase,
            getItemsOfTagsUseCase = getItemsOfTagsUseCase,
            backgroundScheduler = Schedulers.trampoline()
        )
        // act
        viewModel.getItemsOfTags(tagName)
        //assert
        Assert.assertEquals(MenuScreenStates.Error(throwable), viewModel.screenState.value)
    }
}