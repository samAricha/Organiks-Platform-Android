package teka.android.organiks_platform_android.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import teka.android.organiks_platform_android.ui.theme.MainWhiteColor
import teka.android.organiks_platform_android.ui.theme.PrimaryColor
import teka.android.organiks_platform_android.ui.theme.Shapes

@Composable
fun CustomBtn(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shape: Shape = Shapes.medium,
    backgroundColor: Color = PrimaryColor,
    btnText: String,
    textFontSize: TextUnit = TextUnit.Unspecified,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        colors = colors,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = btnText,
            textAlign = TextAlign.Center,
            color = MainWhiteColor,
            fontSize = textFontSize
        )
    }
}