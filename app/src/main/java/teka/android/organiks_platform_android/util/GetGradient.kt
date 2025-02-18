package teka.android.organiks_platform_android.util

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun getGradient(
    startColor: Color,
    endColor: Color,
): Brush {
    return Brush.Companion.horizontalGradient(
        colors = listOf(startColor, endColor)
    )
}