package com.muhammad.myapplication.forvia.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


// Defines th Room database for the app inventory
@Database(entities = [AppInventoryEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppInventoryDatabase : RoomDatabase() {
    abstract fun getAppInventoryDao():AppInventoryDao
    // Abstract function to get the DAO for app inventory operations
}