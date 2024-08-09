package teka.android.organiks_platform_android.presentation.feature_ai_assistant.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daksh.mdparserkit.core.parseMarkdown
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.Mode
import teka.android.organiks_platform_android.ui.theme.BackgroundColor
import teka.android.organiks_platform_android.ui.theme.DecentBlue
import teka.android.organiks_platform_android.ui.theme.DecentGreen
import teka.android.organiks_platform_android.ui.theme.PrimaryLight
import teka.android.organiks_platform_android.ui.theme.quicksand


@Composable
fun MessageItem(
    text: String,
    mode: Mode
) {

    val isGeminiMessage = when (mode) {
        Mode.GEMINI -> true
        Mode.USER -> false
    }

    val bubbleColor =
        if (isGeminiMessage) PrimaryLight
        else MaterialTheme.colorScheme.surface

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val toast = Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_LONG)

    Box(
        contentAlignment = if (!isGeminiMessage) Alignment.CenterEnd else Alignment.CenterStart,
        modifier = Modifier
            .padding(
                start = if (isGeminiMessage) 0.dp else 50.dp,
                end = if (isGeminiMessage) 50.dp else 0.dp,
            )
            .clip(
                RoundedCornerShape(
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp,
                    topEnd = if (isGeminiMessage) 20.dp else 2.dp,
                    topStart = if (isGeminiMessage) 2.dp else 20.dp
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(bubbleColor)
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(30.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(100)),
                        painter = when (mode) {
                            Mode.GEMINI -> painterResource(id = R.drawable.gemini)
                            Mode.USER -> painterResource(id = R.drawable.user)
                        },
                        contentDescription = "logo"
                    )

                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W600, text = when (mode) {
                            Mode.GEMINI -> "GEMINI"
                            Mode.USER -> "FARMER"
                        }
                    )
                }

                TextButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(text))
                        toast.show()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        tint = (if (mode == Mode.USER) DecentBlue else DecentGreen),
                        painter = painterResource(id = R.drawable.baseline_content_copy_24),
                        contentDescription = "copy"
                    )
                    Text(
                        color = (if (mode == Mode.USER) DecentBlue else DecentGreen),
                        fontWeight = FontWeight.W600,
                        text = "COPY",
                        fontSize = 10.sp
                    )
                }
            }
            SelectionContainer {
                if (mode == Mode.GEMINI) {
                    //display text with markdown support
                    Text(
                        modifier = Modifier.padding(start = 35.dp),
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp,
                        text = parseMarkdown(text),
                        fontFamily = quicksand
                    )
                } else {
                    Text(
                        modifier = Modifier.padding(start = 35.dp),
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp,
                        text = text,
                        fontFamily = quicksand
                    )
                }
            }
        }
    }
}