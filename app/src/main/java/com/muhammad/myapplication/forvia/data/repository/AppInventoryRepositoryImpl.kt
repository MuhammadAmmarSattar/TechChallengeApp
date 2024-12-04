package com.muhammad.myapplication.forvia.data.repository

import com.muhammad.myapplication.forvia.core.domain.util.ResultWrapper
import com.muhammad.myapplication.forvia.data.mapper.getAppInventoryList
import com.muhammad.myapplication.forvia.data.mapper.toDomainModelList
import com.muhammad.myapplication.forvia.data.remote.AppInventoryRDS
import com.muhammad.myapplication.forvia.domain.model.AppInventory
import com.muhammad.myapplication.forvia.domain.repository.AppInventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject



/**
 *  i design like that we can fetch from the RDS remote data source or local data source
 *  i can easily handle the cache logic AppInventoryRepositoryImpl
 *
 * Flow allows us to handle streams of data reactively, which is useful for continuous data updates.
 * integrates with Kotlin coroutines, making it easier to handle asynchronous operations
 * */
class AppInventoryRepositoryImpl @Inject constructor(
    private val appInventoryRDS: AppInventoryRDS
) : AppInventoryRepository {

    override suspend fun getApp(): Flow<ResultWrapper<List<AppInventory>>> = flow {
        try {
            when (val remoteResult = appInventoryRDS.getAppInventory()) {
                is ResultWrapper.GenericError -> {
                    emit(ResultWrapper.GenericError(403, "Something went wrong"))
                }
                is ResultWrapper.Success -> {
                    val remoteData = remoteResult.value.getAppInventoryList().toDomainModelList()
                    emit(ResultWrapper.Success(remoteData))
                }
            }
        } catch (e: Exception) {
            emit(ResultWrapper.GenericError(403, "Something went wrong"))
        }
    }
}