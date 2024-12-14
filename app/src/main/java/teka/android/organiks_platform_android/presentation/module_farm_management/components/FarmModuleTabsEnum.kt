package teka.android.organiks_platform_android.presentation.module_farm_management.components;

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.ui.graphics.vector.ImageVector

enum class FarmModuleTabsEnum(
    val text: String,
    val icon: ImageVector
) {
    MyFarms(
        text = "My Farms",
        icon = Icons.Filled.Agriculture
    ),
    FarmTypes(
        text = "Farm Types",
        icon = Icons.Filled.Agriculture
    )
}