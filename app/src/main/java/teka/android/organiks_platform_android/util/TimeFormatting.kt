package teka.android.organiks_platform_android.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalTime.formattedTimeBasedOnTimeFormat(timeFormat: Int): String {
    return if (timeFormat == 12) {
        val hourTo12HourSystem = if (this.hour > 12) {
            this.hour - 12
        } else {
            this.hour
        }
        "$hourTo12HourSystem:${
            this.minute.formattedZeroMinutes()
        } ${if (this.hour > 12) "PM" else "AM"}"
    } else {
        "${this.hour}:${this.minute.formattedZeroMinutes()}"
    }
}

fun String.timeFormat(): Int {
    return if (this == "12-hour") {
        12
    } else {
        24
    }
}

fun Int.timeFormat(): String {
    return if (this == 12) {
        "12-hour"
    } else {
        "24-hour"
    }
}

fun Int.formattedZeroMinutes(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}

fun Long.formattedZeroMinutes(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}

fun Long?.selectedDateMillisToLocalDateTime(): LocalDateTime {
    return Instant.fromEpochMilliseconds(this ?: 0)
        .toLocalDateTime(TimeZone.currentSystemDefault())
}