package com.muhammad.myapplication.forvia.core.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.muhammad.myapplication.forvia.domain.model.AppInventory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {

    val AppType = object : NavType<AppInventory>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): AppInventory? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): AppInventory {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: AppInventory): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: AppInventory) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}