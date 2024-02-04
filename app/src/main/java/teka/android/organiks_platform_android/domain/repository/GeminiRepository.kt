package teka.android.organiks_platform_android.domain.repository

import teka.android.organiks_platform_android.domain.models.ChatStatusModel

interface GeminiRepository {
    suspend fun generate(
        prompt: String,
        images: List<ByteArray> = emptyList()
    ): ChatStatusModel

    fun getApiKey(): String

    fun setApiKey(key: String)
}
