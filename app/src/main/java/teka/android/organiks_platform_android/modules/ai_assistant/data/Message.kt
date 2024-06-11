package teka.android.organiks_platform_android.modules.ai_assistant.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    val text: String,
    val isGenerating: Boolean = false,
    val mode: Mode,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)