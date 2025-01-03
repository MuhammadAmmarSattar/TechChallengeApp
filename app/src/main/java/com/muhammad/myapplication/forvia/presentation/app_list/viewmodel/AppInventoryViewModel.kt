package com.muhammad.myapplication.forvia.presentation.app_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.myapplication.forvia.core.domain.util.ResultWrapper
import com.muhammad.myapplication.forvia.domain.use_case.AppInventoryUseCase
import com.muhammad.myapplication.forvia.domain.use_case.ScheduleNotificationUseCase
import com.muhammad.myapplication.forvia.presentation.app_list.state.AppListState
import com.muhammad.myapplication.forvia.presentation.app_list.event.InventoryListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


// inject the dependency of appRepository with the help of constructor injection.
@HiltViewModel
class AppInventoryViewModel @Inject constructor(
    private val appInventoryUseCase: AppInventoryUseCase,
    private val scheduleNotificationUseCase: ScheduleNotificationUseCase
) : ViewModel() {

    //Holds the mutable state of the app inventory list (Hot flow)
    private val _state = MutableStateFlow(AppListState(isLoading = true))
    val state = _state.asStateFlow() //the state as an immutable flow for observers in UI.

    /**
     *  i use channel flow for one time single event for handling the error messages from API
     *     we can also handle error message via inside the AppListState
     *     whenever configuration changes it shows again same msg.
     *    Unlike StateFlow , it doesn't retain its last value,
     *      making it ideal for one-time events such as error messages,
     *     navigation actions, or showing Snack bars or Toasts
     *
     *     If the user (UI) observes the StateFlow multiple times or gets updated often,
     *     the same error might be handled more than once. that's why i avoid to handle via stateflow inside AppListState
     * */
    private val _events = Channel<InventoryListEvent>()
    val events = _events.receiveAsFlow() // consume as a hot flow

    init {
        appDataListing()
        scheduleNotifications()
    }

    //Fetches app data and updates the state
    fun appDataListing() {
        //Collects each emitted value from the flow.
        viewModelScope.launch {
            appInventoryUseCase.invoke().collectLatest { result ->
                when (result) {
                    is ResultWrapper.Loading -> _state.update { it.copy(isLoading = true) }
                    is ResultWrapper.Success -> {
                        _state.update {
                            it.copy(appInventory = result.value, isLoading = false)
                        }
                    }
//                        _state.value =
//                        AppListState(appInventory = result.value, isLoading = false)

                    is ResultWrapper.GenericError -> {
                        _events.send(InventoryListEvent.Error(error = result.error))
                        _state.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
        // flow will be collected in the viewModelScope but viewmodel is cleared then it will stop the collection.
        //because avoiding any potential memory leaks or unnecessary work....

    }

    fun scheduleNotifications() = scheduleNotificationUseCase.execute()


}