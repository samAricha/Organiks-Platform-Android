package teka.android.organiks_platform_android.util.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import teka.android.organiks_platform_android.ui.theme.TextSizeMedium
import teka.android.organiks_platform_android.ui.theme.quicksand

@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    text: String = "",
    fontFamily: FontFamily = quicksand,
    fontWeight: FontWeight = FontWeight.Light,
    color: Color = DarkGray,
    fontSize: TextUnit = TextSizeMedium
) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        color = color,
        fontSize = fontSize
    )

}