package com.mahmoud_ashraf.menustask.menu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mahmoud_ashraf.domain.menu.models.ItemOfTagModel
import com.mahmoud_ashraf.domain.menu.models.TagsModel
import com.mahmoud_ashraf.domain.menu.usecase.GetItemsOfTagsUseCase
import com.mahmoud_ashraf.domain.menu.usecase.GetTagsUseCase
import com.mahmoud_ashraf.menustask.core.base.BaseViewModel
import com.mahmoud_ashraf.menustask.core.exceptions.MenusException
import com.mahmoud_ashraf.menustask.core.handleError
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class MenuViewModel(
    private val getTagsUseCase: GetTagsUseCase,
    private val getItemsOfTagsUseCase: GetItemsOfTagsUseCase,
    private val backgroundScheduler: Scheduler = Schedulers.io()
) : BaseViewModel() {

    val screenState by lazy { MutableLiveData<MenuScreenStates>() }

    private var canLoading = true
    private var pageNumber = 1

    fun getTags(page: String = pageNumber.toString()) {
        canLoading = false
        screenState.postValue(MenuScreenStates.Loading)
        getTagsUseCase(page)
            .subscribeOn(backgroundScheduler)
            .observeOn(backgroundScheduler)
            .subscribe({
                pageNumber++
                if (it.throwable != null)
                    screenState.postValue(
                        MenuScreenStates.TagsInOfflineMode(handleError(it.throwable ?: return@subscribe), it.data)
                    )
                else
                    screenState.postValue(MenuScreenStates.TagsLoadedSuccessfully(it.data))
                canLoading = it.data.isNotEmpty()
            }, {
                it.printStackTrace()
                screenState.postValue(
                    MenuScreenStates.Error(
                        handleError(it),
                    )
                )


            })
            .also(::addDisposable)
    }

    fun loadMoreTags() {
        if (canLoading) {
            getTags()
        }
    }

    fun getItemsOfTags(tagName: String) {
        screenState.postValue(MenuScreenStates.Loading)
        getItemsOfTagsUseCase(tagName)
            .subscribeOn(backgroundScheduler)
            .observeOn(backgroundScheduler)
            .subscribe({
                if (it.throwable != null)
                    screenState.postValue(
                        MenuScreenStates.ItemsOfTagsOfflineModel(handleError(it.throwable ?: return@subscribe), it.data)
                    )
                else
                screenState.postValue(MenuScreenStates.ItemsOfTagLoadedSuccessfully(it.data))
            }, {
                it.printStackTrace()

            })
            .also(::addDisposable)
    }


}

sealed class MenuScreenStates {
    object Loading : MenuScreenStates()
    data class TagsLoadedSuccessfully(val tags: List<TagsModel>) : MenuScreenStates()
    data class ItemsOfTagLoadedSuccessfully(val items: List<ItemOfTagModel>?) : MenuScreenStates()
    data class TagsInOfflineMode(val error: MenusException, val list: List<TagsModel>) : MenuScreenStates()
    data class ItemsOfTagsOfflineModel(val error: MenusException, val list: List<ItemOfTagModel>) : MenuScreenStates()
    data class Error(val error: MenusException) : MenuScreenStates()
}