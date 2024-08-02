package teka.android.organiks_platform_android.presentation.feature_ai_assistant.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnalystMessage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val alternateText: String = "",
    val isGenerating: Boolean = false,
    val mode: Mode,
    val displayable: Boolean = true,
)