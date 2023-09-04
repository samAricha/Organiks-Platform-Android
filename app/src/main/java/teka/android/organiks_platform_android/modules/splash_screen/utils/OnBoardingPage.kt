package teka.android.organiks_platform_android.modules.splash_screen.utils

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
        description = "Say goodbye to the chaos of tracking your agricultural products. We offer efficient solutions to streamline your record-keeping process."
    )

    object Second : OnBoardingPage(
        image = R.drawable.perfect100,
        title = "Let Us Help You Manage Your Agricultural Data!",
        description = "We're here to simplify your life on the farm. Our tools and expertise will empower you to maintain accurate and organized product records."
    )

    object Third : OnBoardingPage(
        image = R.drawable.letsgo100,
        title = "Embark on a Data-Driven Journey in Agriculture!",
        description = "Get ready to harness the power of data in your agricultural operations. Join us as we explore new ways to optimize and record your valuable products."
    )
}
