package com.mahmoud_ashraf.menustask.core

import com.google.gson.Gson
import com.mahmoud_ashraf.menustask.core.exceptions.ErrorApiResponse
import com.mahmoud_ashraf.menustask.core.exceptions.MenusException
import com.mahmoud_ashraf.data.core.interceptors.NoConnectivityException
import retrofit2.HttpException

fun handleError(it: Throwable): MenusException {
        when (it) {
            is NoConnectivityException -> {
                return MenusException.NoConnection
            }
            is HttpException -> {
                return when (it.code()) {
                    400, 406 -> {
                        val errorResponse = Gson().fromJson(
                            it.response()?.errorBody()?.string(),
                            ErrorApiResponse::class.java
                        )
                        MenusException.Http(errorResponse.errorMessage ?: "")
                    }
                    else -> {
                        MenusException.ServerDown
                    }
                }

            }
            else -> return MenusException.ServerDown
        }

}