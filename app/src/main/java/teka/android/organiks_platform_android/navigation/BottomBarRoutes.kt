package teka.android.organiks_platform_android.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import teka.android.organiks_platform_android.R

enum class BottomBarRoutes(
    val id: Int,
    @StringRes val title: Int,
    val routes: String,
    @DrawableRes val icon: Int
) {

    HOME(1, R.string.homeRoute, "/home", R.drawable.home),
    NOTIFICATION(
        2,
        R.string.notificationRoute, "/notification", R.drawable.monitoring
    ),
    Profile(3, R.string.profileRoute, "/profile", R.drawable.add_to_list)

}