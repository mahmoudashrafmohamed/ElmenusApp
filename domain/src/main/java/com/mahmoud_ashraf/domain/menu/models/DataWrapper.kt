package com.mahmoud_ashraf.domain.menu.models

sealed class Data<T>(val data: T,val throwable: Throwable?=null)
class LocalData<T>(data: T, error: Throwable? = null) : Data<T>(data, error)
class RemoteData<T>(data: T) : Data<T>(data)