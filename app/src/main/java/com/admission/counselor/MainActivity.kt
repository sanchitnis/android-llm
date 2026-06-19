package com.admission.counselor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.admission.counselor.ui.ChatScreen
import com.admission.counselor.ui.theme.AdmissionCounselorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdmissionCounselorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.ui.graphics.Color.White
                ) {
                    val viewModel = hiltViewModel<com.admission.counselor.ui.CounselorViewModel>()
                    ChatScreen(viewModel = viewModel)
                }
            }
        }
    }
}
