package teka.android.organiks_platform_android.modules.ai_assistant.domain

import teka.android.organiks_platform_android.modules.ai_assistant.data.ChatStatusModel


interface GeminiRepository {
    suspend fun generate(
        prompt: String,
        images: List<ByteArray> = emptyList()
    ): ChatStatusModel

    fun getApiKey(): String

}
