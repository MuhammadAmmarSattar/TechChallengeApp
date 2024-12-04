package com.muhammad.myapplication.forvia.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * data Transfer Object (DTO) for representing app inventory data.
 * i need this class is used to parse JSON responses from the API.
 */
data class AppInventoryDto(
    val id: String,
    val name: String,
    @SerializedName("icon")
    val imageUrl: String,
    val rating: Double,
    val size : Long,
    val updated :String,
    @SerializedName("store_name")
    val storeName : String,
    val graphic :String?,
    @SerializedName("vername")
    val versionName : String,
)