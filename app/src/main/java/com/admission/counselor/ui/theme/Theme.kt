package com.admission.counselor.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = RevaOrange,
    secondary = RevaGrey,
    background = White,
    surface = White,
    onPrimary = White,
    onSecondary = White,
    onBackground = RevaGrey,
    onSurface = RevaGrey
)

@Composable
fun AdmissionCounselorTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
