package teka.android.organiks_platform_android.util.helpers;

import android.annotation.SuppressLint
import java.text.NumberFormat
import java.util.Locale

fun String.isLong(): Boolean = this.toLongOrNull() != null

@SuppressLint("DefaultLocale")
fun formatToOneDecimalPlace(value: Double): String {
    return String.format("%.1f", value)
}

fun formatCash(amount: String): String {
    val parsedAmount = amount.toDoubleOrNull() ?: 0.00
    val formatter = NumberFormat.getNumberInstance(Locale.US).apply {
        minimumFractionDigits = 2 // Ensure at least two decimal places
        maximumFractionDigits = 2 // Ensure no more than two decimal places
    }
    return formatter.format(parsedAmount)
}

// Function to format the text as cash
fun formatAsCash(amount: String): String {
    val parsedAmount = amount.toDoubleOrNull() ?: 0.0
    return "ksh. ${"%.2f".format(parsedAmount)}"
}

fun formatCurrency(
    amount: String,
    currencySymbol: String = "ksh."
): String {
    val parsedAmount = amount.toDoubleOrNull() ?: 0.0
    val formatter = NumberFormat.getNumberInstance(Locale.US).apply {
        maximumFractionDigits = 2 // Ensure two decimal places
    }
    val formattedAmount = formatter.format(parsedAmount)
    return "$currencySymbol $formattedAmount"
}

