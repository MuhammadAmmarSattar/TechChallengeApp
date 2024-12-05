package com.muhammad.myapplication.forvia.data.local

import androidx.room.Dao
import androidx.room.Query
import com.muhammad.myapplication.forvia.core.base.BaseDao

@Dao
interface AppInventoryDao : BaseDao<AppInventoryEntity> {

    @Query("SELECT * FROM app_inventory")
    suspend fun getAllApp(): List<AppInventoryEntity>

}