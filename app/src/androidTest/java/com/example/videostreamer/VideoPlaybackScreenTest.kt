package com.example.videostreamer

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.enteties.VideoItem
import com.example.video_playback.VideoPlaybackScreen
import com.example.videostreamer.VideosScreenTest.Companion.TIMEOUT
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class VideoPlaybackScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    private val videoItem = VideoItem(
        id = "0",
        title = "Video Test 1",
        image = "image1",
        url = "https://cdn.coverr.co/videos/coverr-flickering-christmas-lights-5452/1080p.mp4",
        duration = "00:13"
    )

    @Before
    fun setUp() {
        hiltRule.inject()

        composeTestRule.setContent {
            VideoPlaybackScreen(
                videoItem = videoItem,
                viewModel = hiltViewModel(),
                onNavigateBack = {}
            )
        }
    }

    @Test
    fun player_is_displayed() = runTest {
        composeTestRule.waitUntil(7_000) {
            composeTestRule.onNodeWithTag("player_view").isDisplayed()
        }
    }
}