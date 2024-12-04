package com.muhammad.myapplication.forvia.data.remote.response

data class AppInventoryResponseDto(
    val responses: Responses,
    val status: String
    )

    data class Responses(
        val listApps: ListApps
    )

    data class ListApps(
        val datasets: Datasets,
        val info: InfoX
    )

    data class Datasets(
        val all: All
    )

    data class InfoX(
        val status: String,
        val time: Time
    )

    data class All(
        val `data`: Data,
        val info: InfoX
    )

    data class Data(
        val hidden: Int,
        val limit: Int,
        val list: List<AppInventoryDto>,
        val next: Int,
        val offset: Int,
        val total: Int
    )

    data class Time(
        val human: String,
        val seconds: Double
    )