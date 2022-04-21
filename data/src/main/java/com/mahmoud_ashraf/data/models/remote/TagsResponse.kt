package com.mahmoud_ashraf.data.models.remote
import com.google.gson.annotations.SerializedName


data class TagsResponse(
    @SerializedName("tags")
    val tags: List<Tag>
)

data class Tag(
    @SerializedName("photoURL")
    val photoURL: String,
    @SerializedName("tagName")
    val tagName: String
)