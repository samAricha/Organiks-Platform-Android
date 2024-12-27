package teka.android.organiks_platform_android.presentation.module_farm_management.tabs.farm_types

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.presentation.module_farm_management.FarmManagementItemCard
import teka.android.organiks_platform_android.presentation.module_farm_management.FarmMngntInfoCardData
import teka.android.organiks_platform_android.ui.theme.PrimaryVariant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmTypesList(
    navController: NavController
){
    val context = LocalContext.current

    val mngntCards = listOf(
        FarmMngntInfoCardData(
            title = "Fruit Farm",
            value = "0",
            iconResId = R.drawable.vegetables,
            color = PrimaryVariant,
            onClick = {
                navController.navigate(route = AppScreens.FruitListScreen.route)
            }
        ),
        FarmMngntInfoCardData(
            title = "Poultry Farm",
            value = "0",
            iconResId = R.drawable.chicken,
            color = PrimaryVariant,
            onClick = {
                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()

//                navController.navigate(route = AppScreens.CustomerListScreen.route)
            }
        ),
        FarmMngntInfoCardData(
            title = "Animals Farm",
            value = "0",
            iconResId = R.drawable.livestock,
            color = PrimaryVariant,
            onClick = {
                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
//                navController.navigate(route = AppScreens.CustomerListScreen.route)
            }
        )

    )



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 12.dp)
    ) {
        items( mngntCards ) { cardData ->
            FarmManagementItemCard(
                title = cardData.title,
                value = cardData.value,
                iconResId = cardData.iconResId,
                color = cardData.color,
                onClick = cardData.onClick
            )
        }
    }

}