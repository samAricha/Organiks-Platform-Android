package teka.android.organiks_platform_android.domain.authentication

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import teka.android.organiks_platform_android.data.remote.retrofit.AuthService
import teka.android.organiks_platform_android.data.remote.retrofit.RetrofitProvider
import teka.android.organiks_platform_android.domain.authentication.models.LoginRequest
import teka.android.organiks_platform_android.domain.authentication.models.RegisterRequest
import teka.android.organiks_platform_android.repository.DataStoreRepository

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

    suspend fun register(phone: String, email: String, password: String, passwordConfirmation: String): Boolean {
        try {
            val response = authService.registration(
                RegisterRequest(
                    name = "MobileUserName",
                    phone = phone,
                    email = email,
                    password = password,
                    password_confirmation = passwordConfirmation
                )
            )
            if (response.isSuccessful) {
                val token = response.data.accessToken
                saveAuthToken(token)
                return true
            }
        }catch (e: Exception) {
            // Handle the exception here, e.g., log it or perform error handling.
            e.printStackTrace()
            Toast.makeText(context, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show()

        }
        return false
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
