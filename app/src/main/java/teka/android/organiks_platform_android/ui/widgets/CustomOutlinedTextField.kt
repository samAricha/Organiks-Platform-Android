package teka.android.organiks_platform_android.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import teka.android.organiks_platform_android.ui.theme.TxtFieldShapes

@Composable
fun CustomOutlinedTextField(
    modifier: Modifier? = Modifier,
    value: String,
    error: String? = null,
    label: String,
    onValueChanged: (String) -> Unit,
    shape: Shape = TxtFieldShapes.medium,
) {
    Column(
        modifier = modifier ?: Modifier.fillMaxWidth().background(Color.Green)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            label = { Text(label) },
            modifier = modifier ?: Modifier.fillMaxWidth().background(Color.Green),
            shape = shape,
        )
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colors.error
            )
        }
    }
}



@Composable
fun CustomPasswordTextField(
    modifier: Modifier? = Modifier,
    value: String,
    label: String,
    onValueChanged: (String) -> Unit,
    error: String? = null,
    shape: Shape = TxtFieldShapes.medium,
) {
    Column(
        modifier = modifier ?: Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            label = { Text(label) },
            modifier = modifier ?: Modifier.fillMaxWidth(),
            shape = shape,
            visualTransformation = PasswordVisualTransformation()
        )
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colors.error
            )
        }
    }

}

