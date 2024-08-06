package teka.android.organiks_platform_android.presentation.feature_dashborad.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import teka.android.organiks_platform_android.ui.theme.PoppinsLight

@Composable
fun DashboardCard(
    title: String,
    value: String,
    iconResId: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    val iconPainter = painterResource(id = iconResId)

    Card(
        modifier = modifier
            .padding(end = 8.dp),
        elevation = CardDefaults.cardElevation( 4.dp)
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
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = title,
                modifier = Modifier.padding(top = 8.dp),
                fontFamily = PoppinsLight
            )
            Text(
                text = value,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 8.dp),
                fontFamily = PoppinsLight
            )
        }
    }
}