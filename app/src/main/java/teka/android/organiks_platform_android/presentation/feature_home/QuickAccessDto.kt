package teka.android.organiks_platform_android.presentation.feature_home

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class QuickAccessDto(
    val icon: ImageVector,
    val name: String,
    val background: Color,
    val route: String
)
