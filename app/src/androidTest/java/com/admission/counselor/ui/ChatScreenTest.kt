package com.admission.counselor.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.admission.counselor.ui.theme.AdmissionCounselorTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatScreenTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun testUIBRD001_HeaderDisplaysRevaBranding() {
        composeTestRule.setContent {
            AdmissionCounselorTheme {
                HeaderBar()
            }
        }
        
        composeTestRule.onNodeWithText("NAAC A+").assertIsDisplayed()
        composeTestRule.onNodeWithText("REVA Admission Counselor").assertIsDisplayed()
        composeTestRule.onNodeWithText("REVA").assertIsDisplayed()
    }

    @Test
    fun testUIBRD002_UnloadedStateShowsBanner() {
        composeTestRule.setContent {
            AdmissionCounselorTheme {
                UnloadedBanner(reason = "Inactivity timeout exceeded", onReload = {})
            }
        }
        
        composeTestRule.onNodeWithText("Counselor memory released (Inactivity timeout exceeded)").assertIsDisplayed()
        composeTestRule.onNodeWithText("Reload").assertIsDisplayed()
    }
}
