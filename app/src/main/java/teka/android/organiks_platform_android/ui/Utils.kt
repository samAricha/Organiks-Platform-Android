package teka.android.organiks_platform_android.ui

import androidx.annotation.DrawableRes
import teka.android.organiks_platform_android.R

object Utils {
    val productionCategory = listOf(
        Category(title = "Eggs", resId = R.drawable.ic_egg_collection, id = 0),
        Category(title = "Milk", resId = R.drawable.ic_milk_can, id = 1),
        Category(title = "Fruits", resId = R.drawable.ic_fruits, id = 1),
    )
    val stockCategory = listOf(
        Category(title = "Chicken", resId = R.drawable.ic_chicken, id = 0),
        Category(title = "Sheep", resId = R.drawable.ic_sheep, id = 1),
    )
}

data class Category(
    @DrawableRes val resId: Int = -1,
    val title: String = "",
    val id: Int = -1,
)