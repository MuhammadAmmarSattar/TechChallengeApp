package com.muhammad.myapplication.foriva.domain.user_case

import com.muhammad.myapplication.forvia.core.domain.util.ResultWrapper
import com.muhammad.myapplication.forvia.domain.model.AppInventory
import com.muhammad.myapplication.forvia.domain.repository.AppInventoryRepository
import com.muhammad.myapplication.forvia.domain.use_case.AppInventoryUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify


class AppInventoryUseCaseTest {

    private val mockRepository: AppInventoryRepository = mock()
    private lateinit var useCase: AppInventoryUseCase

    @Before
    fun setup() {
        useCase = AppInventoryUseCase(mockRepository)
    }

    @Test
    fun `invoke emits success result when repository returns success`() = runTest {
        // Arrange
        val inventoryList = listOf(
            AppInventory("1", "App 1", "http://example.com/app1.png", 4.5, 12345678, "2024-01-01", "Store 1", "1.0")
        )
        val successResult = flowOf(ResultWrapper.Loading(true), ResultWrapper.Success(inventoryList))
        `when`(mockRepository.getInventoryList()).thenReturn(successResult)
        // Act
        val result = useCase.invoke().toList()
        // Assert
        assertEquals(2, result.size) // Loading + Success
        assertTrue(result.first() is ResultWrapper.Loading)
        assertTrue(result.last() is ResultWrapper.Success)
        assertEquals(inventoryList, (result.last() as ResultWrapper.Success).value)
        verify(mockRepository, times(1)).getInventoryList()
    }

    @Test
    fun `invoke emits loading and success when repository returns success`() = runTest {
        // Arrange
        val mockInventoryList = listOf(
            AppInventory("1", "App 1", "http://example.com/app1.png", 4.5, 12345678, "2024-01-01", "Store 1", "1.0")
        )

        val expectedFlow = flowOf(
            ResultWrapper.Loading(true),
            ResultWrapper.Success(mockInventoryList)
        )

        `when`(mockRepository.getInventoryList()).thenReturn(expectedFlow)

        // Act
        val actualResults = useCase.invoke().toList()

        // Assert
        val loading = actualResults[0] as ResultWrapper.Loading
        val success = actualResults[1] as ResultWrapper.Success

        // Verify the specific properties
        assertEquals(true, loading.isLoading)
        assertEquals(mockInventoryList, success.value)

        verify(mockRepository, times(1)).getInventoryList()
    }

    @Test
    fun `invoke emits loading and empty success when repository returns empty list`() = runTest {
        // Arrange
        val mockEmptyList = emptyList<AppInventory>()

        val expectedFlow = flowOf(
            ResultWrapper.Loading(true),
            ResultWrapper.Success(mockEmptyList)
        )

        `when`(mockRepository.getInventoryList()).thenReturn(expectedFlow)

        // Act
        val actualResults = useCase.invoke().toList()

        val loading = actualResults[0] as ResultWrapper.Loading
        val success = actualResults[1] as ResultWrapper.Success
        // Assert
        assertEquals(true, loading.isLoading)
        assertEquals(mockEmptyList, success.value)


        assertEquals(mockEmptyList, (actualResults.last() as ResultWrapper.Success).value)


        verify(mockRepository, times(1)).getInventoryList()
    }

    @Test
    fun `invoke emits generic error when repository returns error`() = runTest {
        // Arrange
        val errorResult = flowOf(ResultWrapper.Loading(true), ResultWrapper.GenericError("Something went wrong"))
        `when`(mockRepository.getInventoryList()).thenReturn(errorResult)

        // Act
        val result = useCase.invoke().toList()

        // Assert
        assertEquals(2, result.size) // Loading + GenericError
        assertTrue(result.first() is ResultWrapper.Loading)
        assertTrue(result.last() is ResultWrapper.GenericError)
        assertEquals("Something went wrong", (result.last() as ResultWrapper.GenericError).error)
        verify(mockRepository, times(1)).getInventoryList()
    }
}