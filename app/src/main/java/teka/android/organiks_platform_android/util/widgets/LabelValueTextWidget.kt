package teka.android.organiks_platform_android.util.widgets;

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import teka.android.organiks_platform_android.ui.theme.quicksand

@Composable
fun LabelValueTextWidget(
    modifier: Modifier = Modifier,
    label: String = "",
    value: String = "",
    labelFontSize: TextUnit = 14.sp,
    labelFontWeight: FontWeight = FontWeight.Light,
    valueFontWeight: FontWeight = FontWeight.Medium,
    valueFontSize: TextUnit = 14.sp,
    fontFamily: FontFamily = quicksand,
    labelColor: Color = DarkGray,
    valueColor: Color = DarkGray
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = labelFontSize, fontWeight = labelFontWeight, color = labelColor)) {
                append(label)
            }
            withStyle(style = SpanStyle(fontSize = valueFontSize, fontWeight = valueFontWeight, color = valueColor)) {
                append(value)
            }
        },
        fontFamily = fontFamily
    )
}
