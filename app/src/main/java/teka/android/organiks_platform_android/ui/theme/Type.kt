package teka.android.organiks_platform_android.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import teka.android.organiks_platform_android.R

// Set of Material typography styles to start with
val ReemKufi = FontFamily(Font(R.font.reemkufi))
val ReemKufiMedium = FontFamily(Font(R.font.reem_kufi_medium))
val ReemKufiSemiBold = FontFamily(Font(R.font.reem_kufi_semi_bold))
val ReemKufiBold = FontFamily(Font(R.font.reem_kufi_bold))
val Poppins = FontFamily(Font(R.font.poppins))
val PoppinsLight = FontFamily(Font(R.font.poppins_light))
val PoppinsExtraLight = FontFamily(Font(R.font.poppins_extra_light))

val quicksand = FontFamily(
    Font(R.font.quicksand_light, FontWeight.Light),
    Font(R.font.quicksand_regular, FontWeight.Normal),
    Font(R.font.quicksand_medium, FontWeight.Medium),
    Font(R.font.quicksand_semibold, FontWeight.SemiBold),
    Font(R.font.quicksand_bold, FontWeight.Bold)
)


val Typography = Typography(
    defaultFontFamily = quicksand,
//    body1 = TextStyle(
//        fontFamily = ReemKufi,
//        fontWeight = FontWeight.Light,
//        fontSize = 13.sp
//    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)