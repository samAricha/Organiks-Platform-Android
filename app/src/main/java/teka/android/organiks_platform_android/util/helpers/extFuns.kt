package teka.android.organiks_platform_android.util.helpers;

import android.annotation.SuppressLint
import java.text.NumberFormat
import java.util.Locale

fun String.isLong(): Boolean = this.toLongOrNull() != null

@SuppressLint("DefaultLocale")
fun formatToOneDecimalPlace(value: Double): String {
    return String.format("%.1f", value)
}

fun formatCash(amount: Double): String {
    val formatter = NumberFormat.getInstance(Locale.US)
    return formatter.format(amount)
}
