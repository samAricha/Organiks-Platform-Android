package teka.android.organiks_platform_android.util.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import teka.android.organiks_platform_android.util.TextFieldStateMngr

@Composable
fun <T> CustomDropDown(
    modifier: Modifier = Modifier,
    labelText: String = "",
    options: List<T>,
    enabled: Boolean = true,
    isOptional: Boolean = true,
    selectedOption: TextFieldStateMngr,
    onOptionSelected: (T) -> Unit,
    optionTextProvider: @Composable (T) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    maxDropdownHeight: Dp = 200.dp
) {
    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val fieldError = selectedOption.error.collectAsState().value
    val selectedValue = selectedOption.text


    Column(
        modifier = modifier
            .clickable {
                if (enabled) {
                    expanded = !expanded
                }
            }
    ) {

        CustomInputTextField(
            labelText = labelText,
            value = selectedOption,
            onValueChange = {},
            trailingIcon = {
                val icon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
                Icon(
                    imageVector = icon,
                    contentDescription = "Valid",
                    tint = DarkGray
                )
            },
            editable = false,
            isOptional = isOptional,
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp
                )
                .heightIn(max = maxDropdownHeight),
            properties = PopupProperties(focusable = true)
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = {
                        optionTextProvider(selectionOption)
                    },
                    onClick = {
                        onOptionSelected(selectionOption)
                        expanded = false
                    },
                )
            }

        }
    }
}