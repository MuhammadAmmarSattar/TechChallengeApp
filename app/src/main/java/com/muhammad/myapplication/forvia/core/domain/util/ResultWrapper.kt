package com.muhammad.myapplication.forvia.core.domain.util

sealed class ResultWrapper<out T> {
    class Loading<T>(val isLoading: Boolean = true): ResultWrapper<T>()
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val error: String): ResultWrapper<Nothing>()
}