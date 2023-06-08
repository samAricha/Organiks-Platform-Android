package teka.android.organiks_platform_android.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(
        topStart = 28.0.dp,
        topEnd = 28.0.dp,
        bottomEnd = 28.0.dp,
        bottomStart = 28.0.dp
    )
)


val BottomBoxShape = Shapes(
    medium = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomEnd = 0.dp, bottomStart = 0.dp)
)

val InputBoxShape = Shapes(
    medium = RoundedCornerShape(14.dp)
)