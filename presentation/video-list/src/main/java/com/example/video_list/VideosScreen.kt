package com.example.video_list

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.common.HandleContentState
import com.example.domain.enteties.VideoItem
import com.example.domain.enteties.VideoList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideosScreen(
    viewModel: VideoListViewModel = hiltViewModel(),
    onNavigateToPlayback: (VideoItem) -> Unit,
) {
    val videosState by viewModel.videoList.collectAsState()
    var query by remember { mutableStateOf("") }
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullToRefreshState = rememberPullToRefreshState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.loadVideos()
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SearchVideo(
                query = query,
                onClear = {
                    query = ""
                    viewModel.loadVideos()
                },
                onQueryChange = { query = it }
            ) { viewModel.searchVideo(it) }
            HandleContentState(videosState, snackBarHostState = snackBarHostState) {
                VideoList(
                    videos = it,
                    state = pullToRefreshState,
                    onRefresh = viewModel::refresh,
                    isRefreshing = isRefreshing,
                    onNavigateToPlayback = onNavigateToPlayback
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VideoList(
    videos: VideoList,
    modifier: Modifier = Modifier,
    state: PullToRefreshState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onNavigateToPlayback: (VideoItem) -> Unit,
) {
    PullToRefreshBox(
        state = state,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(videos) { videoItem ->
                VideoItemCard(
                    videoItem = videoItem,
                    onNavigateToPlayback = onNavigateToPlayback
                )
            }
        }
    }
}

@Composable
fun SearchVideo(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onClear: () -> Unit,
    onSearch: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = query,
        onValueChange = {
            onQueryChange(it)
        },
        label = { Text("Search Video") },
        modifier = modifier
            .fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onSearch(query)
                keyboardController?.hide()
            }
        ),
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = {
                    onClear()
                }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear text")
                }
            }
        }
    )
}

@Composable
fun VideoItemCard(
    videoItem: VideoItem,
    onNavigateToPlayback: (VideoItem) -> Unit,
) {
    Surface(
        modifier = Modifier
            .clickable { onNavigateToPlayback(videoItem) },
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),

            ) {
            AsyncImage(
                model = videoItem.image,
                contentDescription = "Video thumbnail",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            VideoInfo(videoItem, Modifier.align(Alignment.BottomStart))
        }
    }
}

@Composable
fun VideoInfo(
    videoItem: VideoItem,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.Black.copy(alpha = 0.6f),
            )
            .padding(8.dp)
    ) {
        Text(
            text = videoItem.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = videoItem.duration,
            fontSize = 12.sp,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White
            )
        )
    }
}