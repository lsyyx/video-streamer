package com.example.common

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.core.data_state.Empty
import com.example.core.data_state.Error
import com.example.core.data_state.Exception
import com.example.core.data_state.Loading
import com.example.core.data_state.Success
import com.example.core.data_state.UiState

@Composable
fun <T : Any> HandleContentState(
    state: UiState<T>,
    renderContent: @Composable (T) -> Unit
) {
    when (state) {
        is Loading -> AppLoader()
        is Success -> renderContent(state.data)
        is Error -> {
            Text(text = "Error")
        }

        is Empty -> {
            Text(text = "Empty")
        }

        is Exception -> {
            Log.d("HandleContentStateS", "${state.e}")
            Text(text = "Exception")
        }
    }
}