package com.muhammad.myapplication.forvia.core.presentation.navigation

import com.muhammad.myapplication.forvia.domain.model.AppInventory
import kotlinx.serialization.Serializable

@Serializable
object Home


@Serializable
data class Detail(
    val appInventory: AppInventory
)