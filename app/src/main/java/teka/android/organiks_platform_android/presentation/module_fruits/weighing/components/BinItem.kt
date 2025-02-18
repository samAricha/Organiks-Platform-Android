package teka.android.organiks_platform_android.presentation.module_fruits.weighing.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import teka.android.organiks_platform_android.data.room.entities.WeighingEntity

@Composable
fun BinItem(
    weighingEntity: WeighingEntity,
    onReturnClick: () -> Unit,
    onSourceClick: () -> Unit,
    onDeleteClick: () -> Unit,
    index:Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Bin Number
                Text(
                    text = (index+1).toString(),
                    modifier = Modifier.padding(start = 10.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                // Bin Weight (Centered)
                Text(
                    text = weighingEntity.weight,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )

                // Icons
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
/*                    IconButton(onClick = onReturnClick) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardReturn,
                            contentDescription = "Return to ripening",
                            tint = Color.Black
                        )
                    }

                    IconButton(onClick = onSourceClick) {
                        Icon(
                            imageVector = Icons.Filled.WarningAmber,
                            contentDescription = "Bin source",
                            tint = Color(0xFFFFA500) // Amber color
                        )
                    }*/

                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete bin",
                            tint = Color.Red
                        )
                    }
                }
            }

            // Yellow Divider at Bottom
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(3.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(2.dp))
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}
