package com.muhammad.myapplication.forvia.data.local

import com.muhammad.myapplication.forvia.core.base.BaseLDS
import javax.inject.Inject

/**
 *  class acts as a local data source for app inventory data.
 * It interacts with the local database through the DAO (Data Access Object).
 *
 *  class extends BaseLDS, which is a generic base class for local data sources.
 * It likely provides common functionality for all local data sources.
 * */
class AppInventoryLDS @Inject constructor(private val appDao: AppInventoryDao) :
    BaseLDS<AppInventoryEntity>(appDao) {
    suspend fun getAllInventoryApp(): List<AppInventoryEntity> = appDao.getAllApp()
}