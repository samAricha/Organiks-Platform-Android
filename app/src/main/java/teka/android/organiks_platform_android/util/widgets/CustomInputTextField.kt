package teka.android.organiks_platform_android.util.widgets

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.ui.theme.TextSizeMedium
import teka.android.organiks_platform_android.util.TextFieldStateMngr
import kotlin.text.isNullOrEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomInputTextField(
    modifier: Modifier = Modifier,
    labelText: String = "",
    placeholder: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    value: TextFieldStateMngr,
    maxLines: Int = 1,
    editable: Boolean = true,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(
        fontWeight = FontWeight.SemiBold
    ),
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Companion.Default,
    isOptional: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    debounceDelay: Long = 600L,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()
    val fieldTxt = value.text
    val fieldError = value.error.collectAsState().value
    var job = remember { mutableStateOf<Job?>(null) }


    Column(
        modifier = modifier,
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minWidth = 56.dp)
                .padding(0.dp),
            label = {
                Row {
                    Text(
                        text = "$labelText : ",
                        fontSize = TextSizeMedium,
                        color = DarkGray
                    )
                    if (!isOptional) {
                        Text(
                            text = "*",
                            color = MaterialTheme.colorScheme.error,
                            style = textStyle.copy(fontSize = textStyle.fontSize * 0.75)
                        )
                    }
                }
            },
            value = fieldTxt,
            onValueChange = {
                it -> onValueChange(it)
                job.value?.cancel()
                job.value = CoroutineScope(Dispatchers.Main).launch {
                    delay(debounceDelay)
                    value.validate()
                }
            },
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            textStyle = textStyle,
            shape = shape,
            maxLines = maxLines,
            singleLine = maxLines == 1,
            keyboardOptions = keyboardOptions,
            readOnly = !editable,
            enabled = editable,
            isError = fieldError?.isNotEmpty() == true,
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                unfocusedIndicatorColor = DarkGray,
                disabledContainerColor = Color.Transparent,
            )
        )

        if (!(fieldError.isNullOrEmpty())) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = fieldError,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.End,
            )
        }
    }
}