package com.mahmoud_ashraf.domain.menu.models

sealed class Data<T>(val data: T)
class LocalData<T>(data: T,val error: Throwable? = null) : Data<T>(data)
class RemoteData<T>(data: T) : Data<T>(data)