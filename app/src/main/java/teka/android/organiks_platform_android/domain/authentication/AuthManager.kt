package teka.android.organiks_platform_android.domain.authentication

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
                  private val dataStoreRepository: DataStoreRepository
) {

    private val authService: AuthService = RetrofitProvider.createAuthService()

    suspend fun login(email: String, password: String): Boolean {
        val response = authService.login(LoginRequest(email, password))
        if (response.isSuccessful) {
            val token = response.data.accessToken
            saveAuthToken(token)
            return true
        }
        return false
    }

    suspend fun register(name: String, email: String, password: String, passwordConfirmation: String): Boolean {
        val response = authService.registration(RegisterRequest(name, email, password, passwordConfirmation))
        if (response.isSuccessful) {
            val token = response.data.accessToken
            saveAuthToken(token)
            return true
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
