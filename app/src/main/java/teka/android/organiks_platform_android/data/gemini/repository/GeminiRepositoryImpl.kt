package teka.android.organiks_platform_android.data.gemini.repository

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.ai.client.generativeai.type.content
import teka.android.organiks_platform_android.data.gemini.GeminiService
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.BuildConfig
import teka.android.organiks_platform_android.domain.models.ChatStatusModel
import teka.android.organiks_platform_android.domain.repository.GeminiRepository

class GeminiRepositoryImpl : GeminiRepository {

    private val geminiService = GeminiService()

    override suspend fun generate(
        prompt: String,
        images: List<ByteArray>
    ): ChatStatusModel {
        return try {
            val response = when {
                images.isEmpty() -> geminiService.generateContent(prompt)
                else -> geminiService.generateContentWithMedia(prompt, images)
            }

            val status = response.error?.let {
                ChatStatusModel.Error(it.message)
            } ?: response.getText()?.let {
                ChatStatusModel.Success(it)
            } ?: ChatStatusModel.Error("An error occurred, please retry.")

            status

        } catch (e: IOException) {
            ChatStatusModel.Error("Unable to connect to the server. Please check your internet connection and try again.")
        } catch (e: Exception) {
            ChatStatusModel.Error("An error occurred, please retry.")
        }
    }

    override suspend fun generateResponse(
        prompt: String,
        images: List<Bitmap>
    ): ChatStatusModel {
        return try {
            val response = when {
                images.isEmpty() -> geminiService.generateContent(prompt)
                else -> geminiService.generateAiContentWithMedia(prompt, images)
            }

            val status = response.error?.let {
                ChatStatusModel.Error(it.message)
            } ?: response.getText()?.let {
                ChatStatusModel.Success(it)
            } ?: ChatStatusModel.Error("An error occurred, please retry.")

            status

        } catch (e: IOException) {
            ChatStatusModel.Error("Unable to connect to the server. Please check your internet connection and try again.")
        } catch (e: Exception) {
            ChatStatusModel.Error("An error occurred, please retry.")
        }
    }

    override fun getApiKey(): String {
        return geminiService.getApiKey()
    }

    override fun setApiKey(key: String) {
        geminiService.setApiKey(key)
    }

}