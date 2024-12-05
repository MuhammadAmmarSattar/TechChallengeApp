package com.muhammad.myapplication.forvia.domain.model

import kotlinx.serialization.Serializable


/**
 * Domain model representing app inventory data.
 * This model is used to transfer data from
 * the app listing screen to the app detail screen.
 *
 * i use Serializable for data transfer instead of old Parcelable
 */
@Serializable
data class AppInventory(
    val id: String,
    val name: String,
    val imageUrl: String,
    val rating: Double,
    val size : Long,
    val updated :String,
    val storeName : String,
//    val graphic :String?,
    val versionName : String,
)