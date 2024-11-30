package teka.android.organiks_platform_android.util.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable;
import kotlinx.datetime.LocalTime
import teka.android.organiks_platform_android.util.KotlinxTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    timePickerState: TimePickerState,
    onDismiss: () -> Unit,
    onConfirmTime: (LocalTime) -> Unit,
    ) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val selectedHour = timePickerState.hour
                val selectedMinute = timePickerState.minute
                val selectedTime = KotlinxTime(selectedHour, selectedMinute)
                onConfirmTime(selectedTime.toLocalTime())
            }) {
                Text("OK")
            }
        },
        text = {
            TimePicker(
                state = timePickerState,
            )
        }
    )
}