package com.muhammad.myapplication.forvia.presentation.app_list.event

sealed interface InventoryListEvent {
    data class Error(val error: String): InventoryListEvent
}