package teka.android.organiks_platform_android.presentation.module_farm_management.screens.my_farm.tabs.my_sales

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import timber.log.Timber
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.presentation.module_farm_management.tabs.my_farms_list.FarmItemCard
import teka.android.organiks_platform_android.presentation.module_farm_management.tabs.my_farms_list.MyFarmsListViewModel
import teka.android.organiks_platform_android.ui.theme.TextSizeLarge
import teka.android.organiks_platform_android.util.components.LoadingScreen
import teka.android.organiks_platform_android.util.dialogs.CustomDateRangePickerModal
import teka.android.organiks_platform_android.util.widgets.SimpleSearchInputWidget


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySalesScreen(
    navController: NavController,
    viewModel: MyFarmsListViewModel = hiltViewModel()
) {
    val customersScreenUIState = viewModel.myFarmListUIState.collectAsState().value

    val gatelogSearchQuery = customersScreenUIState.farmSearchQuery
    val farmList = customersScreenUIState.farmList
    val showDatePickerDialog = customersScreenUIState.showDatePickerDialog
    val isFetchingGateLogs = customersScreenUIState.isFetchingFarms



    val dateRangePickerState = rememberDateRangePickerState()
    if (showDatePickerDialog) {
        CustomDateRangePickerModal (
            dateRangePickerState = dateRangePickerState,
            onDismiss = {
                viewModel.toggleDatePickerDialog(false)
            },
            onDateRangeSelected = { selectedRange ->
                viewModel.onDateRangeSelected(selectedRange)
            }
        )
    }



    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(1.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SimpleSearchInputWidget(
                    value = gatelogSearchQuery,
                    onValueChange = { query ->
                        viewModel.updateFarmSearchQuery(query)

                    },
                    modifier = Modifier.weight(1f),
                    placeholderText = "Search ..."
                )

                IconButton(
                    onClick = {
                        viewModel.toggleDatePickerDialog(true)
                              },
                    modifier = Modifier.padding(start = 0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Filter",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 12.dp)
            ) {
                itemsIndexed(farmList) { index, farmEntity ->
                    FarmItemCard(
                        farmEntity = farmEntity,
                        navController = navController
                    )
                }
            }
        }

        if (farmList.isEmpty() && !isFetchingGateLogs) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.farm),
                        contentDescription = "No farms records illustration",
                        modifier = Modifier.size(120.dp),
                        alpha = 0.3f
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No Farms yet ...",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        fontSize = TextSizeLarge,
                        fontFamily = FontFamily.Cursive
                    )
                }
            }
        }

        if (isFetchingGateLogs) {
            LoadingScreen()
        }


        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                Timber.tag("customer").i("EFAB clicked")
                navController.navigate(AppScreens.AddCustomerForm.route)

            },
            icon = { Icon(Icons.Filled.Add, contentDescription = "Add GateLog") },
            text = { Text(text = "Add Customer") },
        )
    }
}