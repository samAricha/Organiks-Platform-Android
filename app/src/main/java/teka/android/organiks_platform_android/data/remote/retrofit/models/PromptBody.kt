package teka.android.organiks_platform_android.data.remote.retrofit.models

import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.models.EggCollection

@Serializable
data class OpenAiPromptModel(
    val prompt: String,
)

