package com.muhammad.myapplication.forvia.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//When retrieving data from the database,
// Room needs to convert the stored JSON string back into a list.
@ProvidedTypeConverter
class Converters {

    // Converts a JSON string to a List<String>
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return value?.let { Gson().fromJson(it, listType) }
    }

    // Converts a List<String> to a JSON string
    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return Gson().toJson(list)
    }
}