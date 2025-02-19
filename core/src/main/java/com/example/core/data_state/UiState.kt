package com.example.core.data_state

sealed class UiState<out T : Any>
data object Loading : UiState<Nothing>()
data class Success<out T : Any>(val data: T) : UiState<T>()
data class Error(val code: Int, val message: String) : UiState<Nothing>()
data object Empty : UiState<Nothing>()
data class Exception(val e: Throwable) : UiState<Nothing>()
