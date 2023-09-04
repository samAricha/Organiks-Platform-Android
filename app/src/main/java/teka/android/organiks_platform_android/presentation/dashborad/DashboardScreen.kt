package teka.android.organiks_platform_android.presentation.dashborad

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import teka.android.organiks_platform_android.ui.theme.PoppinsLight
import teka.android.organiks_platform_android.ui.theme.SecondaryColor

@Composable
fun DashboardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Dashboard",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.h6
        )
        Column(
        ) {
            DashboardCard(
                title = "Not Backed up",
                value = "5",
                iconResId = teka.android.organiks_platform_android.R.drawable.baseline_sync_problem_24,
                color = Color.Yellow
            )
            DashboardCard(
                title = "Egg Collections",
                value = "10",
                iconResId = teka.android.organiks_platform_android.R.drawable.ic_egg_collection,
                color = Color.Green
            )
            DashboardCard(
                title = "Milk Collection",
                value = "50 Litres",
                iconResId = teka.android.organiks_platform_android.R.drawable.ic_milk_can,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    value: String,
    iconResId: Int, // Resource ID for the icon drawable
    color: Color
) {
    // Load the drawable resource and convert it to a Painter
    val iconPainter = painterResource(id = iconResId)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = iconPainter, // Use the loaded drawable
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

