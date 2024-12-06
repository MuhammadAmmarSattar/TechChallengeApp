package com.muhammad.myapplication.foriva.data.repository

import com.muhammad.myapplication.forvia.core.base.Constant.ERROR_MESSAGE
import com.muhammad.myapplication.forvia.core.domain.util.ResultWrapper
import com.muhammad.myapplication.forvia.data.local.AppInventoryEntity
import com.muhammad.myapplication.forvia.data.local.AppInventoryLDS
import com.muhammad.myapplication.forvia.data.mapper.toDomainInventory
import com.muhammad.myapplication.forvia.data.remote.AppInventoryRDS
import com.muhammad.myapplication.forvia.data.repository.AppInventoryRepositoryImpl
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class AppInventoryRepositoryImplTest {

    private val appInventoryRDS: AppInventoryRDS = mock()
    private val appInventoryLDS: AppInventoryLDS = mock()
    private lateinit var appInventoryRepository: AppInventoryRepositoryImpl

    @Before
    fun setup() {
        appInventoryRepository = AppInventoryRepositoryImpl(appInventoryRDS, appInventoryLDS)
    }

    @Test
    fun `fetch inventory list successfully from remote and update local database`() = runTest {
        // Arrange
        val remoteEntities = listOf(
            AppInventoryEntity(
                "1",
                "App 1",
                "http://example.com/app1.png",
                4.5,
                12345678,
                "2024-01-01",
                "Store 1",
                "1.0"
            )
        )
        val domainEntities = remoteEntities.map { it.toDomainInventory() }
        `when`(appInventoryRDS.getAppInventory()).thenReturn(ResultWrapper.Success(remoteEntities))
        `when`(appInventoryLDS.getAllInventoryApp()).thenReturn(remoteEntities)
        // Act
        val result = appInventoryRepository.getInventoryList().toList()
        // Assert
        assertEquals(2, result.size) // Loading + Success
        assertTrue(result.first() is ResultWrapper.Loading)
        assertTrue(result.last() is ResultWrapper.Success)
        assertEquals(domainEntities, (result.last() as ResultWrapper.Success).value)
        verify(appInventoryRDS, times(1)).getAppInventory()
        verify(appInventoryLDS, times(1)).insertAll(remoteEntities)
    }

    @Test
    fun `fetch inventory list from local database when remote fetch fails`() = runTest {
        // Arrange
        val localEntities = listOf(
            AppInventoryEntity(
                "1",
                "App 1",
                "http://example.com/app1.png",
                4.5,
                12345678,
                "2024-01-01",
                "Store 1",
                "1.0"
            )
        )
        val domainEntities = localEntities.map { it.toDomainInventory() }
        `when`(appInventoryRDS.getAppInventory()).thenReturn(ResultWrapper.GenericError(ERROR_MESSAGE))
        `when`(appInventoryLDS.getAllInventoryApp()).thenReturn(localEntities)

        // Act
        val result = appInventoryRepository.getInventoryList().toList()

        // Assert
        assertEquals(2, result.size) // Loading + Success
        assertTrue(result.first() is ResultWrapper.Loading)
        assertTrue(result.last() is ResultWrapper.Success)
        assertEquals(domainEntities, (result.last() as ResultWrapper.Success).value)
        verify(appInventoryRDS, times(1)).getAppInventory()
        verify(appInventoryLDS, times(1)).getAllInventoryApp()
    }

    @Test
    fun  `emit GenericError when both remote and local return empty data `() = runTest {
        // Arrange
        `when`(appInventoryRDS.getAppInventory()).thenReturn(ResultWrapper.Success(emptyList()))
        `when`(appInventoryLDS.getAllInventoryApp()).thenReturn(emptyList())

        // Act
        val result = appInventoryRepository.getInventoryList().toList()
        // Assert
        assertEquals(2, result.size) // Loading + GenericError
        assertTrue(result.first() is ResultWrapper.Loading)
        assertTrue(result.last() is ResultWrapper.GenericError)
        verify(appInventoryRDS, times(1)).getAppInventory()
        verify(appInventoryLDS, times(1)).getAllInventoryApp()
    }
}