package teka.android.organiks_platform_android.presentation.feature_records.screens.remoteRecords.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import teka.android.organiks_platform_android.data.remote.retrofit.models.MilkCollectionResult
import teka.android.organiks_platform_android.ui.theme.PoppinsExtraLight
import teka.android.organiks_platform_android.ui.theme.PoppinsLight
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MilkListItem(
    milkCollection: MilkCollectionResult,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
//            val icon = if (milkCollection.isBackedUp) {
//                painterResource(R.drawable.checkmark) // "Backed Up" icon
//            } else {
//                painterResource(R.drawable.cloud_not_done) // "Not Backed Up" icon
//            }

//            Column(modifier = Modifier.padding(0.dp)) {
//                Image(
//                    painter = icon,
//                    modifier = Modifier.size(24.dp),
//                    contentDescription = if (milkCollection.isBackedUp) "Backed Up" else "Not Backed Up"
//                )
//            }

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Milk", // You can customize the text as needed
                    fontFamily = PoppinsLight
                )
                Text(
                    text = "Qty: ${milkCollection.qty} litres",
                    fontFamily = PoppinsLight
                )
                // Add more properties as needed
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
            ) {
                // Date Text
                val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    .format(milkCollection.date)
                Text(
                    text = formattedDate,
                    fontFamily = PoppinsExtraLight,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}
