package com.mahmoud_ashraf.data.models.remote
import com.google.gson.annotations.SerializedName


data class ItemsOfTagResponse(
    @SerializedName("items")
    val items: List<Item>
)

data class Item(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("photoUrl")
    val photoUrl: String,
    @SerializedName("description")
    val description: String,
)