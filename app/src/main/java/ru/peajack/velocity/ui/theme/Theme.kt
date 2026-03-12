package ru.peajack.velocity.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF5DBD73),
    secondary = Color(0xFF4E7FE5),
    tertiary = Color(0xFFD8A65E),
    background = Color(0xFF111629),
    surface = Color(0xFF111629),
    surfaceContainer = Color(0xFF20293A),
    primaryContainer = Color(0xFF997FEF),
    onBackground = Color(0xFFF8FAFE),
    onSurface = Color(0xFFF8FAFE),
    onSurfaceVariant = Color(0xFF9099AA),
    onPrimary = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF5DBD73),
    secondary = Color(0xFF4E7FE5),
    tertiary = Color(0xFFD8A65E),
    background = Color(0xFFF8FAFC),
    surface = Color(0xFFF8FAFC),
    surfaceContainer = Color.White,
    primaryContainer = Color(0xFF4E7FE5),
    onBackground = Color(0xFF222A3C),
    onSurface = Color(0xFF222A3C),
    onSurfaceVariant = Color(0xFF9EA8B7),
    onPrimary = Color.White
)

@Composable
fun VelocityTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
