package teka.android.organiks_platform_android.util.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import teka.android.organiks_platform_android.domain.models.TextFieldState

@Composable
fun CustomInputTextField(
    modifier: Modifier = Modifier,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    value: TextFieldState,
    maxLines: Int = 1,
    editable: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    Column(
        modifier = modifier,
    ) {
        if (label != null) {
            label()
            Spacer(modifier = Modifier.height(4.dp))
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .defaultMinSize(minWidth = 56.dp),
            value = value.text,
            onValueChange = onValueChange,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            textStyle = textStyle,
            shape = shape,
            maxLines = maxLines,
            singleLine = maxLines == 1,
            keyboardOptions = keyboardOptions,
            readOnly = !editable,
        )
        if (!value.error.isNullOrEmpty()) {
            Text(
                text = value.error,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.error,
                ),
            )
        }
    }
}