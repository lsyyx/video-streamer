package com.example.video_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.common.HandleContentState
import com.example.domain.enteties.VideoItem
import com.example.domain.enteties.VideoList

@Composable
fun VideosScreen(
    viewModel: VideoListViewModel = hiltViewModel(),
    onNavigateToPlayback: (VideoItem) -> Unit = {},
) {
    val videosState by viewModel.videoList.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadVideos()
    }

    Scaffold() { innerPadding ->
        Box {
            HandleContentState(videosState) { VideoList(it, Modifier.padding(innerPadding)) }
        }
    }
}

@Composable
private fun VideoList(
    videos: VideoList,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(videos) { videoItem ->
            VideoItemCard(videoItem)
        }
    }
}


@Composable
fun VideoItemCard(
    videoItem: VideoItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column {
            Box( // Оборачиваем изображение в Box, чтобы добавить поверх него иконку
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = videoItem.image,
                    contentDescription = "Video thumbnail",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    contentScale = ContentScale.Crop
                )

                // Иконка воспроизведения
                Icon(
                    imageVector = Icons.Default.PlayArrow, // Можно заменить на свою иконку
                    contentDescription = "Play video",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(64.dp) // Размер иконки
                        .background(
                            color = Color.Black.copy(alpha = 0.5f), // Полупрозрачный фон
                            shape = CircleShape
                        )
                        .padding(12.dp), // Отступ внутри кружка
                    tint = Color.White // Белый цвет иконки
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = videoItem.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = videoItem.duration,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

