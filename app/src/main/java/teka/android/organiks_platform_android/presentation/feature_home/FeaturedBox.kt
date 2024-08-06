package teka.android.organiks_platform_android.presentation.feature_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.ui.theme.GreenEnd
import teka.android.organiks_platform_android.ui.theme.GreenStart
import teka.android.organiks_platform_android.ui.theme.MainWhiteColor
import teka.android.organiks_platform_android.ui.theme.quicksand

@Composable
fun FeaturedBox(
    totalEggsCollected : String,
    totalMilkCollected: String,
    totalFruitCollected: String
){
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10))
            .background(getGradient(GreenStart, GreenEnd))
    ) {
        Row(
            modifier = Modifier.matchParentSize()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.perfect100),
                modifier = Modifier
                    .padding(start = 30.dp),
//                contentScale = ContentScale.FillBounds,
                contentDescription = "perfect mascot"
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(start = 20.dp),
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = "Farmers Assistant",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = quicksand
                )
                Spacer(modifier = Modifier.size(0.05.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = "Farmers Choice + GEMINI",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = quicksand
                )

                Spacer(modifier = Modifier.height(15.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Column {
                            Text(
                                text = "Eggs : $totalEggsCollected",
                                color = MainWhiteColor,
                                fontSize = 14.sp,
                                )
                            Text(
                                text = "Milk : $totalMilkCollected",
                                color = MainWhiteColor,
                                fontSize = 14.sp,
                                )
                            Text(
                                text = "Fruits : $totalFruitCollected",
                                color = MainWhiteColor,
                                fontSize = 14.sp,
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    FeaturedBox(
        totalEggsCollected = "6",
        totalMilkCollected = "2",
        totalFruitCollected = "2"
    )
}