package com.muhammad.myapplication.forvia.core.domain.util

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: String): ResultWrapper<Nothing>()
}