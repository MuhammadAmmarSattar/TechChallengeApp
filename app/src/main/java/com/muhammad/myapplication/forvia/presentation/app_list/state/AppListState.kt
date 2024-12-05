package com.muhammad.myapplication.forvia.presentation.app_list.state

import androidx.compose.runtime.Immutable
import com.muhammad.myapplication.forvia.domain.model.AppInventory

@Immutable
data class AppListState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val appInventory: List<AppInventory> = emptyList(),
)


