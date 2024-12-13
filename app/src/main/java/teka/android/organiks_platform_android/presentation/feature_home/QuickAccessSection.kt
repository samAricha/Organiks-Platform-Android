package teka.android.organiks_platform_android.presentation.feature_home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material.icons.rounded.PersonAddAlt1
import androidx.compose.material.icons.rounded.StackedLineChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.ui.theme.Cream2
import teka.android.organiks_platform_android.ui.theme.GreenStart
import teka.android.organiks_platform_android.ui.theme.MainWhiteColor
import teka.android.organiks_platform_android.ui.theme.OrangeStart
import teka.android.organiks_platform_android.ui.theme.quicksand

val quickAccessList = listOf(
    QuickAccessDto(
        icon = Icons.Rounded.Dashboard,
        name = "Dashboard",
        background = GreenStart,
        route = AppScreens.RemoteRecordsScreens.route
    ),
    QuickAccessDto(
        icon = Icons.Rounded.StackedLineChart,
        name = "Analytics",
        background = OrangeStart,
        route = AppScreens.DashboardAppScreens.route
    ),

)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuickAccessSection(
    navController: NavController
) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 5.dp)
        ) {
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier
            ) {
                quickAccessList.forEach {
                    QuickAccessItem(
                        quickAccessItem = it,
                        navController = navController,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
}

@Composable
fun QuickAccessItem(
    quickAccessItem: QuickAccessDto,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
    ) {
       Column(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(Cream2)
                .fillMaxWidth()
                .clickable {}
                .padding(horizontal = 5.dp),
           verticalArrangement = Arrangement.Center
       ) {
            Card(
                onClick = { navController.navigate(quickAccessItem.route) },
                colors = CardDefaults.cardColors(
                    containerColor = MainWhiteColor,
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(quickAccessItem.background)
                            .padding(2.5.dp)
                    ) {
                        Icon(
                            imageVector = quickAccessItem.icon,
                            contentDescription = quickAccessItem.name,
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = quickAccessItem.name,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        fontFamily = quicksand
                    )
                }
            }
       }

    }

}













