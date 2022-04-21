package com.mahmoud_ashraf.menustask.menu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mahmoud_ashraf.data.core.exceptions.MenusExceptionWrapper
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
private val backgroundScheduler : Scheduler = Schedulers.io()
) : BaseViewModel() {

     val screenState by lazy { MutableLiveData<MenuScreenStates>() }

    private var canLoading = true
    private var pageNumber = 1

    fun getTags(page : String = pageNumber.toString()) {
        canLoading = false
        screenState.postValue(MenuScreenStates.Loading)
       getTagsUseCase(page)
           .subscribeOn(backgroundScheduler)
           .observeOn(backgroundScheduler)
           .subscribe({
               pageNumber++
               canLoading = if (it.isNotEmpty()) {
                   screenState.postValue(MenuScreenStates.TagsLoadedSuccessfully(it))
                   true
               } else{
                   false
               }
            }, {
                it.printStackTrace()
               when (it) {
                   is MenusExceptionWrapper -> {
                       screenState.postValue(MenuScreenStates.TagsError(
                           handleError(it.throwable),
                           it.data as List<TagsModel>
                       ))
                   }
               }

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
                    screenState.postValue(MenuScreenStates.ItemsOfTagLoadedSuccessfully(it))
            }, {
                it.printStackTrace()
                when (it) {
                    is MenusExceptionWrapper -> {
                            screenState.postValue(MenuScreenStates.ItemsOfTagsError(handleError(it.throwable),it.data as List<ItemOfTagModel>))
                    }
                }
            })
            .also(::addDisposable)
    }


}

sealed class MenuScreenStates {
    object Loading : MenuScreenStates()
    data class TagsLoadedSuccessfully(val tags: List<TagsModel>) : MenuScreenStates()
    data class ItemsOfTagLoadedSuccessfully(val items: List<ItemOfTagModel>?) : MenuScreenStates()
    data class TagsError(val error: MenusException, val list: List<TagsModel>) : MenuScreenStates()
    data class ItemsOfTagsError(val error: MenusException, val list: List<ItemOfTagModel>) : MenuScreenStates()
}