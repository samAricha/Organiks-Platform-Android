package teka.android.organiks_platform_android.domain.models

import android.graphics.Bitmap
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


data class ChatMessageModel(
    val sender: Sender,
    val text: String,
    val images: List<Bitmap> = emptyList(),
    val isLoading: Boolean = false,
) {
    val time: String
        get() = currentTime()

    val isBotMessage: Boolean
        get() = sender == Sender.Bot

    private fun currentTime(): String {
        val datetime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val hour = if (datetime.hour < 10) "0${datetime.hour}" else datetime.hour
        val minute = if (datetime.minute < 10) "0${datetime.minute}" else datetime.minute
        return "${hour}:${minute}"
    }
}

data class AiChatMessageModel(
    val sender: Sender,
    val text: String,
    val images: List<Bitmap> = emptyList(),
    val isLoading: Boolean = false,
) {
    val time: String
        get() = currentTime()

    val isBotMessage: Boolean
        get() = sender == Sender.Bot

    private fun currentTime(): String {
        val datetime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val hour = if (datetime.hour < 10) "0${datetime.hour}" else datetime.hour
        val minute = if (datetime.minute < 10) "0${datetime.minute}" else datetime.minute
        return "${hour}:${minute}"
    }
}

enum class Sender {
    User,
    Bot;

    override fun toString(): String {
        return when (this) {
            User -> "You"
            Bot -> "Assistant"
        }
    }
}