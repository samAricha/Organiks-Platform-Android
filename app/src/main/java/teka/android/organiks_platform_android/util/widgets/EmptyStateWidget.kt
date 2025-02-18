package teka.android.organiks_platform_android.util.widgets


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import teka.android.organiks_platform_android.R



@Composable
fun EmptyStateWidget(
    modifier: Modifier = Modifier.fillMaxSize(),
    imageResource: Int = R.drawable.farm,
    imageDescription: String = "No records illustration",
    imageSize: Dp = 120.dp,
    imageAlpha: Float = 0.3f,
    message: String = "No Records Found",
    messageStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    messageAlignment: TextAlign = TextAlign.Center,
    messageFontSize: TextUnit = 16.sp,
    messageFontFamily: FontFamily = FontFamily.Cursive,
    padding: Dp = 16.dp
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = imageDescription,
                modifier = Modifier.size(imageSize),
                alpha = imageAlpha
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = messageStyle,
                textAlign = messageAlignment,
                fontSize = messageFontSize,
                fontFamily = messageFontFamily
            )
        }
    }
}

