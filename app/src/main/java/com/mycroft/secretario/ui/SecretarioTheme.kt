package com.mycroft.secretario.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val SecretarioGreen = Color(0xFF0F6B3A)
val SecretarioGreenSoft = Color(0xFFE7F3EB)
val SecretarioRed = Color(0xFFB3261E)
val SecretarioBackground = Color(0xFFF7F8F6)
val SecretarioText = Color(0xFF17211B)

private val SecretarioColors = lightColorScheme(
    primary = SecretarioGreen,
    onPrimary = Color.White,
    primaryContainer = SecretarioGreenSoft,
    onPrimaryContainer = SecretarioGreen,
    error = SecretarioRed,
    background = SecretarioBackground,
    onBackground = SecretarioText,
    surface = Color.White,
    onSurface = SecretarioText,
    surfaceVariant = Color(0xFFF0F3F0),
    onSurfaceVariant = Color(0xFF58625B)
)

@Composable
fun SecretarioTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SecretarioColors,
        content = content
    )
}
