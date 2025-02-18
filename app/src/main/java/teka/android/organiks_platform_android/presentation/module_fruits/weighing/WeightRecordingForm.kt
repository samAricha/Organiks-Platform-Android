package teka.android.organiks_platform_android.presentation.module_fruits.weighing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.presentation.module_fruits.weighing.components.BinItem
import teka.android.organiks_platform_android.ui.theme.MainWhiteColor
import teka.android.organiks_platform_android.ui.theme.TextSizeLarge
import teka.android.organiks_platform_android.util.CustomBtn
import teka.android.organiks_platform_android.util.components.LoadingScreen
import teka.android.organiks_platform_android.util.widgets.CustomInputTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeighingForm(
    navController: NavController,
    viewModel: WeighingFormViewModel = hiltViewModel()
){

    val weighingFormUiState = viewModel.weighingFormUIState.collectAsState().value

    val weightList = weighingFormUiState.weightList
    val isFetchingWeights = weighingFormUiState.isFetchingWeights


    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .background(MainWhiteColor),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomInputTextField(
                    modifier = Modifier.weight(1f),
                    labelText = "Fruit Weight(kgs)",
                    value = weighingFormUiState.fruitWeight,
                    onValueChange = {
                        viewModel.updateStringField(WeighingFormUIState::fruitWeight, it)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                CustomBtn(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        when (weighingFormUiState.isUpdatingItem) {
                            true -> {
                                viewModel.addWeight()
                            }

                            false -> {
                                viewModel.addWeight()
                            }
                        }
//                        navController.popBackStack()
                    },
                    btnText = "Record"
                )

            }
        }
    ) { padding: PaddingValues ->

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding()
                    )
            ) {
                Spacer(modifier = Modifier.size(12.dp))

                LazyColumn {
                    itemsIndexed(weightList) { index, weightCollectionEntity ->
                        BinItem(
                            weighingEntity = weightCollectionEntity,
                            onReturnClick = {},
                            onSourceClick = {},
                            onDeleteClick = {},
                            index = index
                        )
                    }

                }


                Spacer(modifier = Modifier.height(34.dp))

            }

            if (weightList.isEmpty() && !isFetchingWeights) {
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
                            painter = painterResource(id = R.drawable.weighing),
                            contentDescription = "No weights records illustration",
                            modifier = Modifier.size(120.dp),
                            alpha = 0.3f
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No Weights Records",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            fontSize = TextSizeLarge,
                            fontFamily = FontFamily.Cursive
                        )
                    }
                }
            }

            if (isFetchingWeights) {
                LoadingScreen()
            }
        }
    }
}


