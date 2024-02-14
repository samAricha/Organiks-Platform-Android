package teka.android.organiks_platform_android.data.gemini

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.ai.client.generativeai.type.content
import teka.android.organiks_platform_android.data.gemini.dto.Response
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import teka.android.organiks_platform_android.BuildConfig
import teka.android.organiks_platform_android.data.gemini.dto.GeminiRequestDto
import java.io.ByteArrayOutputStream


const val TIMEOUT = 30000L

@OptIn(ExperimentalSerializationApi::class, InternalAPI::class)
class GeminiService {
    // region Setup
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                explicitNulls = false
                encodeDefaults = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout) {
            connectTimeoutMillis = TIMEOUT
            socketTimeoutMillis = TIMEOUT
            requestTimeoutMillis = TIMEOUT
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }



    private val baseUrl = "https://generativelanguage.googleapis.com/v1/models"
    private var apiKey: String = BuildConfig.GEMINI_API_KEY

    fun getApiKey(): String {
        return apiKey
    }

    fun setApiKey(key: String) {
        apiKey = key
    }

    suspend fun generateContent(prompt: String): Response {
        return makeApiRequest("$baseUrl/gemini-pro:generateContent?key=$apiKey") {
            addText(prompt)
        }
    }

    suspend fun generateContentWithMedia(prompt: String, images: List<ByteArray>): Response {
        return makeApiRequest("$baseUrl/gemini-pro-vision:generateContent?key=$apiKey") {
            addText(prompt)
            addImages(images)
        }
    }

    suspend fun generateAiContentWithMedia(prompt: String, images: List<Bitmap>): Response {
        return makeApiRequest("$baseUrl/gemini-pro-vision:generateContent?key=$apiKey") {
            addText(prompt)
            addImages(images.map { bitmap ->
                convertBitmapToByteArray(bitmap)
            })
        }
    }

    fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            return outputStream.toByteArray()
        } finally {
            outputStream.close()
        }
    }





    private suspend fun makeApiRequest(
        url: String,
        requestBuilder: GeminiRequestDto.RequestBuilder.() -> Unit
    ): Response {
        val request = GeminiRequestDto.RequestBuilder().apply(requestBuilder).build()

        val response: String = client.post(url) {
            body = Json.encodeToString(request)
        }.bodyAsText()

        return Json.decodeFromString(response)
    }



    val generativeTextModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.GEMINI_API_KEY
    )
    val generativeImageModel = GenerativeModel(
        modelName = "gemini-pro-vision",
        apiKey = BuildConfig.GEMINI_API_KEY
    )
    suspend fun geminiText(prompt: String): GenerateContentResponse {
        return generativeTextModel.generateContent(prompt)
    }
    suspend fun geminiImage(images: List<Bitmap>, prompt: String): GenerateContentResponse {
        val inputContent = content {
            for (inputImage in images) {
                image(inputImage)
            }
            text("What's different between these pictures?")
        }
        return generativeImageModel.generateContent(inputContent)
    }

}