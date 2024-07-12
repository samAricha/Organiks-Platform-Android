package teka.android.organiks_platform_android.domain.authentication

import android.annotation.SuppressLint
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import teka.android.organiks_platform_android.data.remote.retrofit.AuthRetrofitProvider
import teka.android.organiks_platform_android.presentation.feature_auth.core.data.remote.CustomAuthService
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.LoginResultModel
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.RegistrationResult
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.models.LoggedInUser
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.models.LoginRequestBody
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.models.LoginResponseData
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.models.RegisterRequestBody
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.models.RegisterResponseData
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.models.UserRoleDto
import teka.android.organiks_platform_android.domain.repository.DataStoreRepository
import teka.android.organiks_platform_android.util.data.ApiResponseHandler
import timber.log.Timber

import javax.inject.Inject


class AuthManager @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val context: Context // Inject the Context
) {

    private val customAuthService: CustomAuthService = AuthRetrofitProvider.createAuthService()


    @SuppressLint("TimberArgCount")
    suspend fun login(
        username: String,
        password: String
    ): LoginResultModel {
        return try {
            val response: ApiResponseHandler<LoginResponseData> = customAuthService.login(
                LoginRequestBody(username, password)
            )
            Timber.d("FIRSTRESPONSE----> ${response}.")


            if (response.isSuccessful) {
                Timber.d("LOGGED-IN USER----> ${response.data?.user}.")
                val token = response.data?.access_token
                val userData: LoggedInUser? = response.data?.user
                val userRoles: List<UserRoleDto>? = response.data?.user?.roles
                Timber.d("USER ROLES----> ${response.data?.user?.roles}.")


                if (token != null) {
                    saveAuthToken(token)
                }
                if (userData != null) {
                    saveUserData(userData)
                }

                val savedToken: String = getAuthToken()
                val savedUserRoleId: Int = getUserRoleId()
                val savedUserPhone: String? = getUserPhone()
                val savedUserName: String = getUserName()


                Timber.d("USER DATA ----> ${savedToken}...${savedUserRoleId}...${savedUserPhone}...${savedUserName}")
                LoginResultModel.Success(true, userRoles)

            } else {
                var errorMessage = "Wrong Credentials"
                errorMessage = response.message.toString()
                return LoginResultModel.Failure(Exception(response.status?: "Login Failed"), errorMessage)
            }
        } catch (e: Exception) {
            Timber.tag("<<<<<AuthManager>>>>>").e("Log in was unsuccessful", e)
            LoginResultModel.Failure(Exception(e), "Login Failed")
            LoginResultModel.Failure(e)
        }
    }

    suspend fun register(
        phone: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ): RegistrationResult<Boolean> {
        return try {
            val response: ApiResponseHandler<RegisterResponseData> = customAuthService.registration(
                RegisterRequestBody(
                    name = "MobileUser",
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
    private suspend fun saveRoleId(roleId: Int) = withContext(Dispatchers.IO) {
        dataStoreRepository.saveRoleId(roleId)
    }

    private suspend fun saveUserData(userData: LoggedInUser) = withContext(Dispatchers.IO) {
        dataStoreRepository.saveUserData(userData)
    }

    suspend fun getAuthToken(): String {
        return dataStoreRepository.getAccessToken.first()
    }

    suspend fun getUserRoleId(): Int {
        return dataStoreRepository.readLoggedInUserRoleId.first()
    }

    suspend fun getUserPhone(): String? {
        return dataStoreRepository.readLoggedInUserPhone.first()
    }

    suspend fun getUserName(): String {
        return dataStoreRepository.readLoggedInUserName.first()
    }

    suspend fun clearAuthToken() {
        dataStoreRepository.saveToken("")
    }

    suspend fun clearUserData() {
        dataStoreRepository.clearUserData()
    }
}
