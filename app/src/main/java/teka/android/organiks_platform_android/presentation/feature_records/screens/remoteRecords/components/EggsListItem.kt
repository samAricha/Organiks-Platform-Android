package teka.android.organiks_platform_android.presentation.feature_records.screens.remoteRecords.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.ui.theme.PoppinsExtraLight
import teka.android.organiks_platform_android.ui.theme.PoppinsLight
import java.text.SimpleDateFormat
import java.util.Locale



@Composable
fun EggsListItem(
    eggCollection: EggCollectionResult,
    onItemClick: () -> Unit
){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick.invoke()
            }
            .padding(top = 8.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Kienyeji",
                    fontFamily = PoppinsLight,
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp
                )
                Text(text = "Total: ${eggCollection.quantity ?: 0} Eggs",
                    fontFamily = PoppinsLight
                )
                Text(text = "Cracked: ${eggCollection.cracked ?: 0} Eggs",
                    fontFamily = PoppinsLight
                )
            }
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .fillMaxHeight()
            ) {
                // Date Text
                val formattedDate = SimpleDateFormat(
                    "dd-MM-yyyy",
                    Locale.getDefault()
                ).format(eggCollection.date)

                Text(
                    text = formattedDate,
                    fontFamily = PoppinsExtraLight,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                )
            }

        }

    }
}