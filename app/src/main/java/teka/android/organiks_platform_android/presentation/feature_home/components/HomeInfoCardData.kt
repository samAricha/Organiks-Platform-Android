package teka.android.organiks_platform_android.presentation.feature_home.components

import androidx.compose.ui.graphics.Color

data class HomeInfoCardData(
    val title: String,
    val value: String,
    val iconResId: Int,
    val color: Color,
    val onClick: ()->Unit
)