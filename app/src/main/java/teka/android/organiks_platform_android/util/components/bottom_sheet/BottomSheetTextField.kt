package teka.android.organiks_platform_android.util.components.bottom_sheet;

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import teka.android.organiks_platform_android.util.TextFieldStateMngr
import teka.android.organiks_platform_android.util.widgets.CustomInputTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetTextField(
    modifier: Modifier = Modifier,
    labelText: String = "",
    placeholderText: String = "Select Person",
    currentTextState: String,
    editable: Boolean = false,
    error: String? = null,
    isOptional: Boolean = false,
    onClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .clickable {
                expanded = !expanded
                onClick()
            }
    ) {
        CustomInputTextField(
            labelText = labelText,
            value = TextFieldStateMngr(currentTextState),
            onValueChange = {},
            trailingIcon = {
                val icon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
                Icon(
                    imageVector = icon,
                    contentDescription = "Valid",
                    tint = DarkGray
                )
            },
            editable = editable,
            isOptional = isOptional,
        )
    }

}
