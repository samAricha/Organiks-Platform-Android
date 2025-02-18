package teka.android.organiks_platform_android.util.widgets;

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import teka.android.organiks_platform_android.R


@Composable
fun LoadingScreen(
    logoResId: Int = R.drawable.farm,
    modifier: Modifier = Modifier,
    logoSize: Dp = 100.dp,
    logoAlpha: Float = 0.2f
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Companion.Center
    ) {
        Image(
            painter = painterResource(id = logoResId),
            contentDescription = null,
            modifier = Modifier
                .size(logoSize)
                .alpha(logoAlpha)
        )
        CircularProgressIndicator()
    }
}