package com.muhammad.myapplication.forvia.presentation.app_list.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.myapplication.forvia.core.domain.util.ResultWrapper
import com.muhammad.myapplication.forvia.presentation.app_list.state.AppListState
import com.muhammad.myapplication.forvia.domain.use_case.AppInventoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject


// inject the dependency of appDataUseCase with the help of constructor injection.
@HiltViewModel
class AppInventoryViewModel @Inject constructor(private val appDataUseCase: AppInventoryUseCase) : ViewModel(){

    //Holds the mutable state of the app inventory list
    private val _state = MutableStateFlow(AppListState(isLoading = true))
    val state = _state.asStateFlow() //the state as an immutable flow for observers in UI.

    init {
        appDataListing()
    }

    //Fetches app data and updates the state
    private fun appDataListing() {
        //Collects each emitted value from the flow.
        appDataUseCase.invoke().onEach {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            when (it) {
                is ResultWrapper.GenericError ->{}
                is ResultWrapper.Success -> {
                    _state.value = AppListState(isLoading = false)
                    _state.value = AppListState(appInventory = it.value)
                }
            }
        }.launchIn(viewModelScope) // flw will be collected in the viewModelScope but viewmodel is cleared then it will stop the collection.
        //because avoiding any potential memory leaks or unnecessary work.
    }
}