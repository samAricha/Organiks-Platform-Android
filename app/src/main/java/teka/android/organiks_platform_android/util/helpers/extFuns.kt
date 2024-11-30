package teka.android.organiks_platform_android.util.helpers;

fun String.isLong(): Boolean = this.toLongOrNull() != null

fun formatToOneDecimalPlace(value: Double): String {
    return String.format("%.1f", value)
}
