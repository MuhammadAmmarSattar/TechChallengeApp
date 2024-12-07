package com.muhammad.myapplication.foriva.presentation.viewmodel

import app.cash.turbine.test
import com.muhammad.myapplication.forvia.core.base.Constant.ERROR_MESSAGE
import com.muhammad.myapplication.forvia.core.base.Constant.NO_DATA_AVAIABLE
import com.muhammad.myapplication.forvia.core.domain.util.ResultWrapper
import com.muhammad.myapplication.forvia.domain.model.AppInventory
import com.muhammad.myapplication.forvia.domain.use_case.AppInventoryUseCase
import com.muhammad.myapplication.forvia.domain.use_case.ScheduleNotificationUseCase
import com.muhammad.myapplication.forvia.presentation.app_list.event.InventoryListEvent
import com.muhammad.myapplication.forvia.presentation.app_list.viewmodel.AppInventoryViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class AppInventoryViewModelTest {

    @get:Rule(order = 3)
    val mainDispatcherRule = MainDispatcherRule()

    // Mock dependencies
    private val appInventoryUseCase: AppInventoryUseCase = mock()
    private val scheduleNotificationUseCase: ScheduleNotificationUseCase = mock()

    // ViewModel instance
    private lateinit var viewModel: AppInventoryViewModel

    @Before
    fun setup() {
        viewModel = AppInventoryViewModel(appInventoryUseCase, scheduleNotificationUseCase)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after each test
    }

    @Test
    fun `appDataListing emit success  state when data is available`() = runTest {
        //Arrange
        val mockInventoryList = listOf(
            AppInventory(
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
        `when`(appInventoryUseCase.invoke()).thenReturn(
            flowOf(
                ResultWrapper.Success(mockInventoryList),
            )
        )
        // Act
        viewModel.appDataListing()
        // Assert
        Assert.assertEquals(mockInventoryList, viewModel.state.value.appInventory)

    }

    @Test
    fun  `first time launcnh app without internet connection response generic error`() = runTest {
        // Arrange
        `when`(appInventoryUseCase.invoke()).thenReturn(flowOf(ResultWrapper.GenericError(
            ERROR_MESSAGE
        )))

        viewModel.appDataListing()
        // Act: Trigger the function that collects the flow
        viewModel.appDataListing()

        // Assert: Use Turbine to test the emissions
        // Check the single event emitted in _events
        // handle error all via channel. flow events
        viewModel.events.test {
            val event = awaitItem()
            assertTrue(event is InventoryListEvent.Error)
            assertEquals(ERROR_MESSAGE, (event as InventoryListEvent.Error).error) // Match the error message
            cancelAndConsumeRemainingEvents() // Clean up
        }
    }


    @Test
    fun `appDataListing empty response check the channel flow event generic error msg`() = runTest {
        // Arrange: Mock the use case to emit a GenericError result
        val errorMessage = NO_DATA_AVAIABLE
        val errorResult = ResultWrapper.GenericError(errorMessage)

        `when`(appInventoryUseCase.invoke()).thenReturn(
            flowOf(errorResult)
        )

        // Act: Trigger the function that collects the flow
        viewModel.appDataListing()

        // Assert: Use Turbine to test the emissions
        // Check the single event emitted in _events
        // handle error all via channel. flow events
        viewModel.events.test {
            val event = awaitItem()
            assertTrue(event is InventoryListEvent.Error)
            assertEquals(
                errorMessage,
                (event as InventoryListEvent.Error).error
            ) // Match the error message
            cancelAndConsumeRemainingEvents() // Clean up
            this.cancel()
        }
    }
}

class MainDispatcherRule(val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) :
    TestWatcher() {
    override fun starting(description: Description?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
    }

}