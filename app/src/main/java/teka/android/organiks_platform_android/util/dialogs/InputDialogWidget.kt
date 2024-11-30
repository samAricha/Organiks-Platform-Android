package teka.android.organiks_platform_android.util.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import teka.android.organiks_platform_android.ui.theme.TextSizeLarge
import teka.android.organiks_platform_android.ui.theme.TextSizeMedium

@Composable
fun InputDialogWidget(
    title: String = "",
    label: String = "",
    initialValue: String = "",
    onSubmit: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var inputText = remember { mutableStateOf(initialValue) }
    var errorMessage = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title, fontSize = TextSizeLarge) },
        text = {
            Column {
                TextField(
                    value = inputText.value,
                    onValueChange = { inputText.value = it },
                    label = { Text(text = label, fontSize = TextSizeMedium) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    )
                )
                if (errorMessage.value.isNotEmpty()) {
                    Text(
                        text = errorMessage.value,
                        fontWeight = FontWeight.Light,
                        fontSize = TextSizeLarge,
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSubmit(inputText.value)
                }
            ) {
                Text("Submit")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
