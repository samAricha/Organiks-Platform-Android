package teka.android.denitracker.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import teka.android.denitracker.ui.theme.ReemKufiMedium
import teka.android.denitracker.ui.theme.ReemKufiSemiBold
import teka.android.denitracker.ui.theme.buttonShapes


@Composable
fun CustomButton(
    modifier: Modifier? = Modifier,
    onClick: () -> Unit,
    shape: Shape = buttonShapes.large,
    content: String
) {
    Column(
        modifier = modifier ?: Modifier.fillMaxWidth().padding(0.dp)
    ) {
        Button(
            onClick = onClick,
            shape = shape,
            modifier = modifier ?: Modifier.fillMaxWidth().padding(0.dp)
        ) {
            Text(
                text = content,
                fontFamily = ReemKufiSemiBold
            )
        }
    }

}


