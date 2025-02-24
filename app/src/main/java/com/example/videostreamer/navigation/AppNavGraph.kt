package com.example.videostreamer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.domain.enteties.VideoItem
import com.example.home.HomeScreen
import com.example.video_list.VideosScreen
import com.example.video_playback.VideoPlaybackScreen
import kotlin.reflect.typeOf

@Composable
fun AppNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        composable<HomeRoute> { HomeScreen() { navController.navigate(VideosRoute) } }
        composable<VideosRoute> {
            VideosScreen() { videoItem ->
                navController.navigate(VideoPlaybackRoute(videoItem))
            }
        }
        composable<VideoPlaybackRoute>(
            typeMap = mapOf(
                typeOf<VideoItem>() to VideoNavType.VideoType
            )
        ) { backStackEntry ->
            val arg = backStackEntry.toRoute<VideoPlaybackRoute>()
            VideoPlaybackScreen(
                videoItem = arg.videoItem,
            ) { navController.popBackStack() }
        }
    }
}