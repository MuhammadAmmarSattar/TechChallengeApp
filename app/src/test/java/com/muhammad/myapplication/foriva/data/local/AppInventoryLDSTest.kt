package com.muhammad.myapplication.foriva.data.local

import com.muhammad.myapplication.forvia.data.local.AppInventoryDao
import com.muhammad.myapplication.forvia.data.local.AppInventoryEntity
import com.muhammad.myapplication.forvia.data.local.AppInventoryLDS
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class AppInventoryLDSTest {

    private val appDao: AppInventoryDao = mock()
    private lateinit var appInventoryLDS: AppInventoryLDS

    @Before
    fun setup(){
        appInventoryLDS = AppInventoryLDS(appDao)
    }

    @Test
    fun `test getAllInventoryApp returns data successfully`() = runTest {
        // Arrange
        val expectedEntities = listOf(
            AppInventoryEntity(
                id = "1",
                name = "App 1",
                imageUrl = "http://example.com/app1.png",
                rating = 4.5,
                size = 12345678,
                updated = "2024-01-01",
                storeName = "Store 1",
                versionName = "1.0"
            ),
            AppInventoryEntity(
                id = "2",
                name = "App 2",
                imageUrl = "http://example.com/app2.png",
                rating = 4.0,
                size = 98765432,
                updated = "2024-01-02",
                storeName = "Store 2",
                versionName = "2.0"
            )
        )
        `when`(appDao.getAllApp()).thenReturn(expectedEntities)
        // Act
        val result = appInventoryLDS.getAllInventoryApp()
        // Assert
        assertEquals(expectedEntities, result)
        verify(appDao, times(1)).getAllApp()
    }

    @Test
    fun `test insertAll inserts data successfully`() = runTest {
        // Arrange
        val entitiesToInsert = listOf(
            AppInventoryEntity(
                id = "1",
                name = "App 1",
                imageUrl = "http://example.com/app1.png",
                rating = 4.5,
                size = 12345678,
                updated = "2024-01-01",
                storeName = "Store 1",
                versionName = "1.0"
            )
        )
        // Act..
        appInventoryLDS.insertAll(entitiesToInsert)
        // Assert
        verify(appDao, times(1)).insertAll(entitiesToInsert)
    }
}