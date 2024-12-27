package teka.android.organiks_platform_android.presentation.module_farm_management

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.presentation.module_farm_management.components.FarmModuleTabsEnum
import teka.android.organiks_platform_android.presentation.module_farm_management.tabs.farm_types.FarmTypesList
import teka.android.organiks_platform_android.presentation.module_farm_management.tabs.my_farms_list.MyFarmsListScreen
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagementListScreen(
    navController: NavController,
) {

    val farmModuleTabs = FarmModuleTabsEnum.entries
    val pagerState = rememberPagerState(pageCount = { farmModuleTabs.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val scope = rememberCoroutineScope()



    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(1.dp))

            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth().padding(0.dp),
//                edgePadding = 4.dp
            ) {
                farmModuleTabs.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.outline,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(currentTab.ordinal)
                            }
                        },
                        text = {
                            Text(
                                text = currentTab.text,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) { page ->
                when (farmModuleTabs[page]) {
                    FarmModuleTabsEnum.MyFarms ->  MyFarmsListScreen(navController = navController)
                    FarmModuleTabsEnum.FarmTypes -> FarmTypesList(navController = navController)
                }
            }


        }

        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                Timber.tag("new farm").i("EFAB clicked")
                navController.navigate(AppScreens.CreateFarmScreen.route)
            },
            icon = { Icon(Icons.Filled.Add, contentDescription = "Create New Farm") },
            text = { Text(text = "Create New Farm") },
        )
    }


}