package com.muhammad.myapplication.forvia.core.base

import androidx.room.Insert
import androidx.room.OnConflictStrategy

/**generic BaseLDS (Local Data Source) class
 *
 * By using generics, you can reuse the same class for different types
 * of entities. This reduces the need to write repetitive code for
 * each entity type, making your codebase cleaner and more maintainable.*/
open class BaseLDS<T> constructor(private val baseDao: BaseDao<T>) {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T) = baseDao.insert(obj)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<T>) = baseDao.insertAll(list)

    suspend fun update(obj: T) = baseDao.update(obj)

    suspend fun delete(obj: T) = baseDao.delete(obj)

}