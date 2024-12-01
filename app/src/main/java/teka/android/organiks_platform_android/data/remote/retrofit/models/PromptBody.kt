package teka.android.organiks_platform_android.data.remote.retrofit.models

import kotlinx.serialization.Serializable

@Serializable
data class OpenAiPromptModel(
    val prompt: String,
)

