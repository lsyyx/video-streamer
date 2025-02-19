package com.example.home

import android.util.Log
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    onNavigateToVideoList: () -> Unit = {}
) {
    Log.d("HomeScreen", "HomeScreen called")
    onNavigateToVideoList()
}
