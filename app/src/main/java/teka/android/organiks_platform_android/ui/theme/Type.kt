package teka.android.organiks_platform_android.ui.theme

import androidx.compose.material3.Typography
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


private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = quicksand),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = quicksand),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = quicksand),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = quicksand),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = quicksand),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = quicksand),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = quicksand),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = quicksand),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = quicksand),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = quicksand),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = quicksand),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = quicksand),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = quicksand),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = quicksand),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = quicksand)
)