package com.muhammad.myapplication.forvia.core.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update


/**i create BaseDao with a generic type benefits:
 * Reusability
 * Type Safety
 * Consistency: basic CRUD operations (
 * Create, Read, Update, Delete) across different entities.
 * This makes it easier to maintain and understand the code.
 *
 * Flexibility:we can extend the BaseDao to add more
 * specific operations for different entities
 * while still keeping the common operations centralized.
 * */
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<T>)

    @Update
    fun update(obj: T)

    @Delete
    suspend fun delete(obj: T)
}