package teka.android.organiks_platform_android.domain.authentication

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import teka.android.organiks_platform_android.data.remote.retrofit.AuthService
import teka.android.organiks_platform_android.data.remote.retrofit.RetrofitProvider
import teka.android.organiks_platform_android.domain.authentication.models.LoginRequest
import teka.android.organiks_platform_android.domain.authentication.models.RegisterRequest
import teka.android.organiks_platform_android.modules.auth.core.util.RegistrationResult
import teka.android.organiks_platform_android.modules.auth.core.util.models.RegisterRequestBody
import teka.android.organiks_platform_android.modules.auth.core.util.models.RegisterResponseData
import teka.android.organiks_platform_android.repository.DataStoreRepository
import teka.android.organiks_platform_android.util.data.ApiResponseHandler
import timber.log.Timber

import javax.inject.Inject


class AuthManager @Inject constructor(
                  private val dataStoreRepository: DataStoreRepository,
                  private val context: Context // Inject the Context

) {

    private val authService: AuthService = RetrofitProvider.createAuthService()

    suspend fun login(username: String, password: String): Boolean {
        try {
            val response = authService.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                val token = response.data.accessToken
                saveAuthToken(token)
                return true
            }
        }catch (e: Exception) {
            // Handle the exception here, e.g., log it or perform error handling.
            e.printStackTrace()
            Toast.makeText(context, "Login failed. Please try again.", Toast.LENGTH_SHORT).show()

        }
        return false
    }

    suspend fun register(
        phone: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ): RegistrationResult<Boolean> {
        return try {
            val response: ApiResponseHandler<RegisterResponseData> = authService.registration(
                RegisterRequestBody(
                    name = "AdminUser",
                    phone = phone,
                    email = email,
                    password = password,
                    password_confirmation = passwordConfirmation
                )
            )
            Timber.d("FIRSTRESPONSE----> ${response}.")

            if (response.isSuccessful) {
                Timber.d("SUCCESSRESPONSE----> .")

                RegistrationResult.Success(true)
            } else {
                Timber.d("NOSUCCESSRESPONSE----> .")
                val errorMessage = response.message
                RegistrationResult.Failure(Exception("Validation Error"), errorMessage)
            }
        } catch (e: Exception) {
            Timber.d("EXCEPTIONRESPONSE----> ${e.message}.")
            RegistrationResult.Failure(Exception("Validation Error"), e.message)

            // Handle the exception here
            if (e is HttpException) {
                val errorResponse: ResponseBody? = e.response()?.errorBody()
                if (errorResponse != null) {
                    // Parse the error body as JSON
                    val errorJsonString: String = errorResponse.string()
                    val errorJsonObject = JSONObject(errorJsonString)

                    // Extract specific error message from JSON
                    val errorMessage = errorJsonObject.getString("message")

                    return RegistrationResult.Failure(Exception(errorResponse.toString() ?: "Unknown error"))
                }else{
                    return RegistrationResult.Failure(Exception("Unknown error"))
                }
            } else {
                RegistrationResult.Failure(Exception("Validation Error"), e.message)
            }
        }
    }

    private suspend fun saveAuthToken(token: String) = withContext(Dispatchers.IO) {
        dataStoreRepository.saveToken(token)
    }

    suspend fun getAuthToken(): String {
        return dataStoreRepository.getAccessToken.first()
    }

    suspend fun clearAuthToken() {
        dataStoreRepository.saveToken("")
    }
}
