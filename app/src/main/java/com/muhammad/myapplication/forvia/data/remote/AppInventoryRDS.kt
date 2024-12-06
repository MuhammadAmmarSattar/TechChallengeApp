package com.muhammad.myapplication.forvia.data.remote

import com.muhammad.myapplication.forvia.core.data.util.safeApiCall
import com.muhammad.myapplication.forvia.core.domain.util.ResultWrapper
import com.muhammad.myapplication.forvia.data.local.AppInventoryEntity
import com.muhammad.myapplication.forvia.data.mapper.toEntityList
import com.muhammad.myapplication.forvia.data.remote.response.AppInventoryResponseDto
import com.muhammad.myapplication.forvia.data.remote.service.AppInventoryService
import com.muhammad.myapplication.forvia.domain.model.AppInventory
import javax.inject.Inject


/**
 * This class is responsible for remote data operations. It separates the data fetching logic from
 *  other parts of the application am following here single responsibility principle.
 * */
class AppInventoryRDS @Inject constructor(private val  appService: AppInventoryService) {

    suspend fun getAppInventory(): ResultWrapper<List<AppInventoryEntity>> {
        return safeApiCall{appService.getAppInventory().toEntityList()}
    }
}