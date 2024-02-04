package teka.android.organiks_platform_android.modules.auth

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.domain.authentication.AuthManager
import teka.android.organiks_platform_android.domain.form.validation.use_cases.ValidateEmailUseCase
import teka.android.organiks_platform_android.domain.form.validation.use_cases.ValidatePasswordUseCase
import teka.android.organiks_platform_android.repository.DataStoreRepository
import javax.inject.Inject

data class LoginState(
    val isLoading: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val dataStoreRepository: DataStoreRepository
    ) : ViewModel() {

    var isLoggedInState: Flow<Boolean> = dataStoreRepository.readLoggedInState()

    private var _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    private var _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _isRegistered = MutableStateFlow(false)
    val isRegistered: StateFlow<Boolean> = _isRegistered

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = loginState.value.copy(isLoading = true)
            val success = authManager.login(username, password)
            _isLoggedIn.value = success
            if (success){
                dataStoreRepository.saveLoggedInState(isLoggedIn = success)
            }
            _loginState.value = loginState.value.copy(isLoading = true)
        }
    }

    fun register(
        phone: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        viewModelScope.launch {
            val success = authManager.register(
                phone = phone,
                email = email,
                password = password,
                passwordConfirmation = passwordConfirmation
            )
            _isRegistered.value = success
            if (success)dataStoreRepository.saveLoggedInState(isLoggedIn = success)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authManager.clearAuthToken()
            dataStoreRepository.saveLoggedInState(false)
        }
    }

}


@SuppressLint("CompositionLocalNaming")
val UserState = compositionLocalOf<AuthViewModel> { error("User State Context Not Found!") }