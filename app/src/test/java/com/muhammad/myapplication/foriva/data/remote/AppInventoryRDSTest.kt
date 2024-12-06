package com.muhammad.myapplication.foriva.data.remote

import com.muhammad.myapplication.forvia.core.domain.util.ResultWrapper
import com.muhammad.myapplication.forvia.data.local.AppInventoryEntity
import com.muhammad.myapplication.forvia.data.remote.AppInventoryRDS
import com.muhammad.myapplication.forvia.data.remote.response.All
import com.muhammad.myapplication.forvia.data.remote.response.AppInventoryDto
import com.muhammad.myapplication.forvia.data.remote.response.AppInventoryResponseDto
import com.muhammad.myapplication.forvia.data.remote.response.Data
import com.muhammad.myapplication.forvia.data.remote.response.Datasets
import com.muhammad.myapplication.forvia.data.remote.response.InfoX
import com.muhammad.myapplication.forvia.data.remote.response.ListApps
import com.muhammad.myapplication.forvia.data.remote.response.Responses
import com.muhammad.myapplication.forvia.data.remote.response.Time
import com.muhammad.myapplication.forvia.data.remote.service.AppInventoryService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class AppInventoryRDSTest {

    private val appService: AppInventoryService = mock() // Mock the service
    private lateinit var appInventoryRDS: AppInventoryRDS

    @Before
    fun setup() {
        appInventoryRDS = AppInventoryRDS(appService) // Inject the mocked service
    }

    @Test
    fun `test success response`() = runTest {

        // Arrange
        val expectedResponse = mockResponseDto
        `when`(appService.getAppInventory()).thenReturn(expectedResponse)


        // Expected mapping
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
            )
        )
        // Act
        val result = appInventoryRDS.getAppInventory()
        // Assert
        assertTrue(result is ResultWrapper.Success)
        val entities = (result as ResultWrapper.Success).value
        // Verify the size and individual fields
        assertEquals(expectedEntities.size, entities.size)
        assertEquals(expectedEntities, entities) // You can also compare the entire list
    }


    @Test
    fun `test API failure with generic error`() = runTest {
        // Arrange
        val errorMessage = "Server error"
        `when`(appService.getAppInventory()).thenThrow(HttpException(Response.error<Any>(500, ResponseBody.create(null, ""))))

        // Act
        val result = appInventoryRDS.getAppInventory()

        // Assert
        assertTrue(result is ResultWrapper.GenericError)
        val error = result as ResultWrapper.GenericError
        assertEquals(errorMessage, error.error)
    }
    @Test
    fun `test unexpected exception`() = runTest {
        // Arrange
        `when`(appService.getAppInventory()).thenThrow(RuntimeException("Unexpected error"))

        // Act
        val result = appInventoryRDS.getAppInventory()

        // Assert
        assertTrue(result is ResultWrapper.GenericError)
        val error = result as ResultWrapper.GenericError
        assertEquals("Unexpected error", error.error)
    }
    @Test
    fun `test empty list response`() = runTest {
        // Arrange
        val emptyResponseDto = AppInventoryResponseDto(
            responses = Responses(
                listApps = ListApps(
                    datasets = Datasets(
                        all = All(
                            data = Data(
                                hidden = 0,
                                limit = 10,
                                list = emptyList(), // Empty list here
                                next = 0,
                                offset = 0,
                                total = 0
                            ),
                            info = InfoX(
                                status = "success",
                                time = Time(human = "1s", seconds = 1.0)
                            )
                        ),
                    ),
                    info = InfoX(
                        status = "success",
                        time = Time(human = "1s", seconds = 1.0)
                    )
                )
            ),
            status = "success"
        )

        `when`(appService.getAppInventory()).thenReturn(emptyResponseDto)

        // Act
        val result = appInventoryRDS.getAppInventory()
        // Assert
        assertTrue(result is ResultWrapper.Success)
        val entities = (result as ResultWrapper.Success).value
        assertTrue(entities.isEmpty())
    }
}

val mockResponseDto = AppInventoryResponseDto(
    responses = Responses(
        listApps = ListApps(
            datasets = Datasets(
                all = All(
                    data = Data(
                        hidden = 0,
                        limit = 10,
                        list = listOf(
                            AppInventoryDto(
                                id = "1",
                                name = "App 1",
                                imageUrl = "http://example.com/app1.png",
                                rating = 4.5,
                                size = 12345678,
                                updated = "2024-01-01",
                                storeName = "Store 1",
                                versionName = "1.0"
                            )
                        ),
                        next = 0,
                        offset = 0,
                        total = 1
                    ),
                    info = InfoX(
                        status = "success",
                        time = Time(human = "1s", seconds = 1.0)
                    )
                ),
            ),
            info = InfoX(
                status = "success",
                time = Time(human = "1s", seconds = 1.0)
            ),
        ),
    ),
    status = "success"
)


