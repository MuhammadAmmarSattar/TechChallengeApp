package com.muhammad.myapplication.forvia.data.repository

import com.muhammad.myapplication.forvia.core.base.Constant.ERROR_MESSAGE
import com.muhammad.myapplication.forvia.core.domain.util.ResultWrapper
import com.muhammad.myapplication.forvia.data.local.AppInventoryLDS
import com.muhammad.myapplication.forvia.data.mapper.toDomainInventory
import com.muhammad.myapplication.forvia.data.mapper.toEntityList
import com.muhammad.myapplication.forvia.data.remote.AppInventoryRDS
import com.muhammad.myapplication.forvia.domain.model.AppInventory
import com.muhammad.myapplication.forvia.domain.repository.AppInventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject


/**
 *  i design like that we can fetch from the RDS remote data source or local data source
 *  i can easily handle the cache logic AppInventoryRepositoryImpl
 *
 * Flow allows us to handle streams of data reactively, which is useful for continuous data updates.
 * integrates with Kotlin coroutines, making it easier to handle asynchronous operations
 * */
class AppInventoryRepositoryImpl @Inject constructor(
    private val appInventoryRDS: AppInventoryRDS,
    private val appInventoryLDS: AppInventoryLDS
) : AppInventoryRepository {

    /**
     * Note:
     * The choice of caching strategy depends on our specific requirements and the performance of the backend server.
     * We need to evaluate both to decide which caching logic is the best fit.
     *
     * i choose the strategy of SSOT
     */
     override suspend fun getInventoryList(): Flow<ResultWrapper<List<AppInventory>>> = flow {
        try {
            /**
             *          Case 1:
             * SSOT = Single Source of Truth principle
             * - Always fetch data from the remote source.
             * - Update the local database with the fetched data.
             * - Query the local database to get the latest data.
             *
             *  - single source for data is accurate and reliable,
             *    as all updates are made to one central source.
             */
            emit(ResultWrapper.Loading(true))
            val remoteResult = appInventoryRDS.getAppInventory()
            if (remoteResult is ResultWrapper.Success) {
                appInventoryLDS.insertAll(remoteResult.value.toEntityList())
                val localResult = appInventoryLDS.getAllInventoryApp().map { it.toDomainInventory() }
                emit(ResultWrapper.Success(localResult))
            }else {
                val localResult = appInventoryLDS.getAllInventoryApp().map { it.toDomainInventory() }
                if(localResult.isNotEmpty()){
                    emit(ResultWrapper.Success(localResult))
                }else {
                    emit(ResultWrapper.GenericError(ERROR_MESSAGE))
                }
            }
            /**
             * Case 2:
             * 1. Hit the API: val remoteResult = appInventoryRDS.getAppInventory()
             * 2. If the API call is successful, emit the data to the ViewModel sametime insert it into the local database.
             * 3. If the API call fails (e.g., GenericError, Internet connection error), fetch the data from the local database.
             *
             * Pros:
             * - Reduces the number of local database queries.
             * - Always provides the latest data from the server.
             *
             * Cons:
             * - Puts a load on the backend server, especially with millions of users.
             * because in every case we hit the API.
             */

            /**
             * Case 3:
             * 1. First, check the local database: val localResult = appInventoryLDS.getAllInventoryApp().map { it.toAppInventory() }
             * 2. If the local database is empty, hit the API to fetch remote data.
             * 3. If the API call is successful, emit the data and insert it into the local database.
             * 4. Next time, automatically fetch data from the local database, regardless of internet availability.
             *
             * Pros:
             * - Saves API calls, reducing the load on the server.
             * - Improves server performance.
             *
             * Cons:
             * - Users might see outdated data if the local database is not updated frequently.
             */
        } catch (e: IOException){
            emit(ResultWrapper.GenericError("IOException: ${e.localizedMessage}"))
        }
        catch (e: HttpException) {
            emit(ResultWrapper.GenericError( e.message()))
        }
        catch (e: Exception) {
            emit(ResultWrapper.GenericError( e.cause?.message.toString()))
        }
    }
}



