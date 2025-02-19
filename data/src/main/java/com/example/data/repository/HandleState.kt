package com.example.data.repository

import com.example.core.data_state.*
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any, R : Any> handleState(
    execute: suspend () -> Response<T>,
    transform: (T) -> R
): UiState<R> {
    return try {
        val response = execute()
        val body = response.body()
        return when {
            body == null -> Empty
            response.isSuccessful -> Success(transform(body))
            else -> Error(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        Exception(e)
    }
}