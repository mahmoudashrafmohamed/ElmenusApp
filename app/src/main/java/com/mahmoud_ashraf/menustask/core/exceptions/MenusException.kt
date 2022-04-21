package com.mahmoud_ashraf.menustask.core.exceptions

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException

sealed class MenusException : Throwable(){
    object NoConnection : MenusException()
    data class Http(val msg : String) : MenusException()
    object UnAuthorized : MenusException()
    data class Business(val msg : String) : MenusException()
    object ServerDown : MenusException()
}

fun HttpException.mapToErrorMsg(): String {
    val errorResponse = Gson().fromJson(
        response()?.errorBody()?.string(),
        ErrorApiResponse::class.java
    )
    return errorResponse.errorMessage ?: "something wrong happened!"
}

class ErrorApiResponse {
    @SerializedName("data")
    val errorMessage : String?=null
}
