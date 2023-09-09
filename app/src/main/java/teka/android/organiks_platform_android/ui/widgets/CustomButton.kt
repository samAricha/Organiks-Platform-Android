package teka.android.organiks_platform_android.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import teka.android.organiks_platform_android.ui.theme.ReemKufiMedium
import teka.android.organiks_platform_android.ui.theme.ReemKufiSemiBold
import teka.android.organiks_platform_android.ui.theme.buttonShapes


@Composable
fun CustomButton(
    modifier: Modifier? = Modifier,
    onClick: () -> Unit,
    shape: Shape = buttonShapes.large,
    content: String
) {
    Column(
        modifier = modifier ?: Modifier
            .padding(0.dp)
    ) {
        Button(
            onClick = onClick,
            shape = shape,
            modifier = modifier ?: Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {
            Text(
                text = content,
                fontFamily = ReemKufiMedium
            )
        }
    }

}

@Preview
@Composable
fun CustomButtonPreview(){
    CustomButton(onClick = { /*TODO*/ }, content = "Test Log")
}


