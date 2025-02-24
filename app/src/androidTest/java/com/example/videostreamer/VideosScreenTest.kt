package com.example.videostreamer

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class VideosScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    companion object {
        const val TIMEOUT = 7_000L
    }

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun loader_displayed_when_data_is_loading() = runTest {
        composeTestRule.onNodeWithTag("loader").assertIsDisplayed()
    }

    @Test
    fun search_bar_is_displayed_successfully() = runTest {
        composeTestRule.onNodeWithText("Search Video").assertIsDisplayed()
    }

    @Test
    fun video_list_contains_items() = runTest {
        composeTestRule.waitUntil(TIMEOUT) {
            composeTestRule.onAllNodes(hasTestTag("video_item")).fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onAllNodes(hasTestTag("video_item"))
            .assertCountEquals(2)
    }

    @Test
    fun search_bar_works() = runTest {
        composeTestRule.onNodeWithText("Search Video").performTextInput("video")
        composeTestRule.onNodeWithText("Search Video").performImeAction()
        composeTestRule.waitUntil(TIMEOUT) {
            composeTestRule.onAllNodesWithTag("video_item").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onAllNodesWithTag("video_item").assertAny(hasText("Video Test 1"))
    }

    @Test
    fun navigate_to_playback_screen() = runTest {
        composeTestRule.waitUntil(TIMEOUT) {
            composeTestRule.onAllNodesWithTag("video_item").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Video Test 1").performClick()

        composeTestRule.waitUntil(TIMEOUT) {
            composeTestRule.onNodeWithTag("player_view").isDisplayed()
        }
    }

    @Test
    fun navigate_from_playback_to_videos() = runTest {
        composeTestRule.waitUntil(TIMEOUT) {
            composeTestRule.onAllNodesWithTag("video_item").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Video Test 1").performClick()

        composeTestRule.waitUntil(TIMEOUT) {
            composeTestRule.onNodeWithTag("player_view").isDisplayed()
        }

        composeTestRule.onNodeWithTag("back_to_videos").performClick()
        composeTestRule.waitUntil(TIMEOUT) {
            composeTestRule.onAllNodesWithTag("video_item").fetchSemanticsNodes().isNotEmpty()
        }
    }
}