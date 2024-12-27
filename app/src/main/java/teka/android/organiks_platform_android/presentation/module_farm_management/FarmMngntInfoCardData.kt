package teka.android.organiks_platform_android.presentation.module_farm_management

import androidx.compose.ui.graphics.Color

data class FarmMngntInfoCardData(
    val title: String,
    val value: String,
    val iconResId: Int,
    val color: Color,
    val onClick: ()->Unit
)