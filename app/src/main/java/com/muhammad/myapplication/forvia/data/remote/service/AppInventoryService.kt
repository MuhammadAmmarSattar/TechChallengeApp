package com.muhammad.myapplication.forvia.data.remote.service

import com.muhammad.myapplication.forvia.data.remote.response.AppInventoryResponseDto
import retrofit2.http.GET


/**
 * service interface for fetching app inventory data from the Aptoide API.
 * this interface defines the network operations required to retrieve the list of apps.
 *
 */
interface AppInventoryService {
    @GET("bulkRequest/api_list/listApps")
    suspend fun getAppInventory(): AppInventoryResponseDto
/**
 * this function is a suspend function, meaning it should be called from a coroutine or another suspend function.
 */

}