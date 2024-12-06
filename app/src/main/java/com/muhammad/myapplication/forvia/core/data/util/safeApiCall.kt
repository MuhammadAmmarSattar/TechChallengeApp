package com.muhammad.myapplication.forvia.core.data.util

import com.muhammad.myapplication.forvia.core.domain.util.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher = Dispatchers.IO, apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
//                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    // 500 code
                    ResultWrapper.GenericError( "Server error")
                }
                else -> {
                    ResultWrapper.GenericError( throwable.message ?: "Something went wrong")
                }
            }
        }
    }
}