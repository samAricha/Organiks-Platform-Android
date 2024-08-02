package teka.android.organiks_platform_android.presentation.feature_ai_assistant.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    val text: String,
    val isGenerating: Boolean = false,
    val mode: Mode,
    val displayable: Boolean = true,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)