package com.mahmoud_ashraf.menustask.menu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mahmoud_ashraf.domain.menu.models.TagsModel
import com.mahmoud_ashraf.domain.menu.usecase.GetItemsOfTagsUseCase
import com.mahmoud_ashraf.domain.menu.usecase.GetTagsUseCase
import com.mahmoud_ashraf.menustask.core.base.BaseViewModel
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class MenuViewModel(
    private val getTagsUseCase: GetTagsUseCase,
    private val getItemsOfTagsUseCase: GetItemsOfTagsUseCase,
private val backgroundScheduler : Scheduler = Schedulers.io()
) : BaseViewModel() {

    internal val screenState by lazy { MutableLiveData<MenuScreenStates>() }

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
                screenState.postValue(MenuScreenStates.Error(it))
            })
            .also(::addDisposable)
    }

    fun loadMoreTags() {
        if (canLoading) {
            getTags()
        }
    }


}

sealed class MenuScreenStates {
    object Loading : MenuScreenStates()
    data class TagsLoadedSuccessfully(val tags: List<TagsModel>) : MenuScreenStates()
    data class Error(val error: Throwable) : MenuScreenStates()
}