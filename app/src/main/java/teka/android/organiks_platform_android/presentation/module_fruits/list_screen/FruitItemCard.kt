package teka.android.organiks_platform_android.presentation.module_fruits.list_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.data.room.entities.FruitCollectionEntity
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.ui.theme.PrimaryColor
import teka.android.organiks_platform_android.ui.theme.PureWhiteColor
import teka.android.organiks_platform_android.ui.theme.Shapes
import teka.android.organiks_platform_android.ui.theme.TextSizeMedium
import teka.android.organiks_platform_android.ui.theme.TextSizeXLarge
import teka.android.organiks_platform_android.util.CustomBtn
import teka.android.organiks_platform_android.util.convertMillisToStringDate
import teka.android.organiks_platform_android.util.widgets.CustomText
import teka.android.organiks_platform_android.util.widgets.LabelValueTextWidget

@Composable
fun FruitItemCard(
    fruitCollectionEntity: FruitCollectionEntity,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = Shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = PureWhiteColor
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.vegs_no_bg),
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(top = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 16.dp, end = 8.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomText(
                            text = fruitCollectionEntity.fruitTypeId ?: "N/A",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = TextSizeXLarge
                        )
                        CustomText(
                            text = convertMillisToStringDate(fruitCollectionEntity.date),
                            fontSize = TextSizeMedium,
                            fontWeight = FontWeight.ExtraLight,
                            fontFamily = FontFamily.Cursive,
                            color = PrimaryColor
                        )
                    }

                    LabelValueTextWidget(label = "Quantity(kgs): ", value = fruitCollectionEntity.qty ?: "N/A")

                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider()
            CustomBtn(
                btnText = "Invoice",
                onClick = {
                    navController.navigate(AppScreens.CreateInvoiceScreen.createRoute(fruitCollectionEntity.uuid))
                }
            )
        }

    }
}


