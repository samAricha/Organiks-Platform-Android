package teka.android.organiks_platform_android.ui

import androidx.annotation.DrawableRes
import teka.android.organiks_platform_android.R

object Utils {
    val category = listOf(
        Category(title = "Chicken", resId = R.drawable.ic_chicken, id = 0),
        Category(title = "Eggs", resId = R.drawable.ic_egg_collection, id = 1),

    )
}

data class Category(
    @DrawableRes val resId: Int = -1,
    val title: String = "",
    val id: Int = -1,
)