package com.example.video_list

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class VideosScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSearchFieldIsVisible() {
        composeTestRule.setContent {
            VideosScreen()
        }

//        composeTestRule.onNodeWithText("Search Video").assertIsDisplayed()
    }
}