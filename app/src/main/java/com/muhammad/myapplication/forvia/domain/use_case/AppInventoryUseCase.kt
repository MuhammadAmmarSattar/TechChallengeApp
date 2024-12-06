package com.muhammad.myapplication.forvia.domain.use_case

import com.muhammad.myapplication.forvia.core.domain.util.ResultWrapper
import com.muhammad.myapplication.forvia.domain.model.AppInventory
import com.muhammad.myapplication.forvia.domain.repository.AppInventoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


//encapsulates a specific business logic or action, in this case, fetching app inventory data.
class AppInventoryUseCase @Inject constructor(
    private val repository: AppInventoryRepository //provide the necessary dependencies via hilt
) {

     suspend fun invoke(): Flow<ResultWrapper<List<AppInventory>>> {
        return repository.getInventoryList()
    }

}