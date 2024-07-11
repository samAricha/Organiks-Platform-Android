package teka.android.organiks_platform_android.presentation.feature_ai_assistant.domain

import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.ChatStatusModel


interface GeminiRepository {
    suspend fun generate(
        prompt: String,
        images: List<ByteArray> = emptyList()
    ): ChatStatusModel

    fun getApiKey(): String

}
