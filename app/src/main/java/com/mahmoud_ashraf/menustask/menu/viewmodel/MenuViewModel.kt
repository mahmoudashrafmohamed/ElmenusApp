package com.mahmoud_ashraf.menustask.menu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mahmoud_ashraf.domain.menu.models.*
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

    init {
        getTags()
    }

     fun getTags(page: String = pageNumber.toString()) {
        canLoading = false
        screenState.postValue(MenuScreenStates.Loading)
        getTagsUseCase(page)
            .subscribeOn(backgroundScheduler)
            .observeOn(backgroundScheduler)
            .map(::handleTagsLogic)
            .onErrorReturn(::handleTagsError)
            .subscribe(screenState::postValue)
            .also(::addDisposable)
    }

    private fun handleTagsLogic(tags: Data<List<TagsModel>>): MenuScreenStates {
        pageNumber++
        canLoading = tags.data.isNotEmpty()
        return when (tags) {
            is RemoteData -> {
                if (tags.data.isEmpty() && isCurrentFirstPage(pageNumber-1))
                    MenuScreenStates.TagsEmptyState
                else
                    MenuScreenStates.TagsLoadedSuccessfully(tags.data)
            }
            is LocalData -> {
                if (tags.data.isEmpty()  && isCurrentFirstPage(pageNumber-1))
                    MenuScreenStates.TagsOfflineModeEmptyState(
                        handleError(tags.error ?: Throwable("Unknown Exception"))
                    )
                else MenuScreenStates.TagsInOfflineMode(
                    handleError(tags.error ?: Throwable("Unknown Exception")), tags.data
                )
            }
        }
    }

    private fun isCurrentFirstPage(page: Int): Boolean {
        return page==1
    }

    private fun handleTagsError(error: Throwable): MenuScreenStates =
         MenuScreenStates.Error(handleError(error))

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
            .map(::handleItemsOfTagLogic)
            .onErrorReturn(::handleItemsOfTagsError)
            .subscribe(screenState::postValue)
            .also(::addDisposable)
    }

    private fun handleItemsOfTagsError(error: Throwable)=
         MenuScreenStates.Error(
            handleError(error),
        )

    private fun handleItemsOfTagLogic(items: Data<List<ItemOfTagModel>>): MenuScreenStates {
        return when (items) {
            is RemoteData -> {
                if (items.data.isEmpty())
                    MenuScreenStates.ItemsOfTagsEmptyState
                else
                    MenuScreenStates.ItemsOfTagLoadedSuccessfully(items.data)
            }
            is LocalData -> {
                if (items.data.isEmpty())
                    MenuScreenStates.ItemsOfTagsOfflineModelModeEmptyState(
                        handleError(
                            items.error ?: Throwable("Unknown Exception")
                        )
                    )
                else MenuScreenStates.ItemsOfTagsOfflineMode(
                    handleError(
                        items.error ?: Throwable("Unknown Exception")
                    ), items.data
                )
            }
        }
    }


}

sealed class MenuScreenStates {
    object Loading : MenuScreenStates()

    /*tags*/
    data class TagsLoadedSuccessfully(val tags: List<TagsModel>) : MenuScreenStates()
    object TagsEmptyState : MenuScreenStates()
    data class TagsInOfflineMode(val error: MenusException, val list: List<TagsModel>) : MenuScreenStates()
    data class TagsOfflineModeEmptyState(val error: MenusException) : MenuScreenStates()

    /*itemsOfTag*/
    data class ItemsOfTagLoadedSuccessfully(val items: List<ItemOfTagModel>?) : MenuScreenStates()
    object ItemsOfTagsEmptyState : MenuScreenStates()
    data class ItemsOfTagsOfflineMode(val error: MenusException, val list: List<ItemOfTagModel>) : MenuScreenStates()
    data class ItemsOfTagsOfflineModelModeEmptyState(val error: MenusException) : MenuScreenStates()


    data class Error(val error: MenusException) : MenuScreenStates()
}