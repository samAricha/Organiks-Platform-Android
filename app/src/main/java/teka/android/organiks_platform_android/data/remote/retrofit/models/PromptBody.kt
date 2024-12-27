package teka.android.organiks_platform_android.data.remote.retrofit.models

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class OpenAiPromptModel(
    val prompt: String,
)

