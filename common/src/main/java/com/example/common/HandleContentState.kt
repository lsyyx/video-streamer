package com.example.common

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.core.data_state.Empty
import com.example.core.data_state.Error
import com.example.core.data_state.Exception
import com.example.core.data_state.Loading
import com.example.core.data_state.Success
import com.example.core.data_state.UiState

@Composable
fun <T : Any> HandleContentState(
    state: UiState<T>,
    snackBarHostState: SnackbarHostState,
    renderContent: @Composable (T) -> Unit
) {
    when (state) {
        is Loading -> AppLoader()
        is Success -> renderContent(state.data)
        is Error -> {
            LaunchedEffect(state) {
                snackBarHostState.showSnackbar("Error: ${state.message}")
            }
        }

        is Empty -> {
            LaunchedEffect(state) {
                snackBarHostState.showSnackbar("There is no video for this query")
            }
        }

        is Exception -> {
            LaunchedEffect(state) {
                snackBarHostState.showSnackbar("Error: ${state.e}")
            }
        }
    }
}