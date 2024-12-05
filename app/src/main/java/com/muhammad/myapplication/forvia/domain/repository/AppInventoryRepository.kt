package com.muhammad.myapplication.forvia.domain.repository

import com.muhammad.myapplication.forvia.core.domain.util.ResultWrapper
import com.muhammad.myapplication.forvia.domain.model.AppInventory
import kotlinx.coroutines.flow.Flow

/**
 * layer contains business logic
 *
 * By using an interface, we ensure that the data fetching logic is abstracted and can be
 * implemented differently without affecting the rest of the app.
 *
 * This allows for flexibility and easier testing.
 * */
interface AppInventoryRepository {
   suspend  fun  getInventoryList(): Flow<ResultWrapper<List<AppInventory>>>
   /** flow because we have cache local database, emits multiple values over period of time thats exactly we want that.
      before loading the data from local or remote we emit loading loading , success and faiure
   **/
}