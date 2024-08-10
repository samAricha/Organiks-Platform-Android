package teka.android.organiks_platform_android.presentation.feature_splash_screen.utils

import androidx.annotation.DrawableRes
import teka.android.organiks_platform_android.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.amazed100,
        title = "Tired of Disorganized Product Records?",
        description = "Say goodbye to the chaos of tracking your agricultural products."
    )

    object Second : OnBoardingPage(
        image = R.drawable.perfect100,
        title = "Let Us Help You Manage Your Agricultural Data!",
        description = "We're here to simplify your life on the farm."
    )

    object Third : OnBoardingPage(
        image = R.drawable.gemini_organiks_no_bg,
        title = "Embark on a Data-Driven Journey in Agriculture!",
        description = "With the help of GEMINI AI from Google you can now use your data and get insightful advice."
    )
}
