package com.muhammad.myapplication.forvia.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/** This is the entity class for the database table
 * every column name should follow the camel case as database column name */
@Entity(tableName = "app_inventory")
data class AppInventoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "app_id")
    val id: String,

    @ColumnInfo(name = "app_name")
    val name: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "app_rating")
    val rating: Double,

    @ColumnInfo(name = "app_size")
    val size: Long,

    @ColumnInfo(name = "last_updated")
    val updated: String,

    @ColumnInfo(name = "store_name")
    val storeName: String,

    @ColumnInfo(name = "graphic_url")
    val graphic: String,

    @ColumnInfo(name = "version_name")
    val versionName: String,
)