package com.mahmoud_ashraf.domain.core

import com.mahmoud_ashraf.domain.menu.models.Data
import com.mahmoud_ashraf.domain.menu.models.LocalData
import io.reactivex.rxjava3.core.Single


fun <T> loadRemoteData(
    remote: Single<Data<T>>,
    local: Single<LocalData<T>>,
    saveData: (data: T) -> Unit
): Single<Data<T>> {
    return loadDataFromRemote(remote, saveData).onErrorResumeNext { t->
        local.map {
            LocalData(it.data,t)
        }
    }
}

private fun <T> loadDataFromRemote(
    remote: Single<Data<T>>,
    saveData: (data: T) -> Unit
): Single<Data<T>> {
    return remote.doOnSuccess { saveData(it.data) }
}