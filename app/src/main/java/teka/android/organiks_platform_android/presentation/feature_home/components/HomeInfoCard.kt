package teka.android.organiks_platform_android.presentation.feature_home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import teka.android.organiks_platform_android.ui.theme.PoppinsLight

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeInfoCard(
    title: String,
    value: String,
    iconResId: Int,
    color: Color,
    onClick: ()->Unit,
    modifier: Modifier = Modifier
) {
    val iconPainter = painterResource(id = iconResId)

    Card(
        modifier = modifier
            .padding(end = 8.dp),
        elevation = 4.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = iconPainter,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(58.dp)
            )
            Text(
                text = title,
                modifier = Modifier.padding(top = 8.dp),
                fontFamily = PoppinsLight,
                textAlign = TextAlign.Center
            )
//            Text(
//                text = value,
//                style = TextStyle(fontWeight = FontWeight.Bold),
//                modifier = Modifier.padding(top = 8.dp),
//                fontFamily = PoppinsLight
//            )
        }
    }
}