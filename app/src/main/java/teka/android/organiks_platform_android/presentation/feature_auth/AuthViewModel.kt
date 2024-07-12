package teka.android.organiks_platform_android.presentation.feature_auth

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.domain.authentication.AuthManager
import teka.android.organiks_platform_android.domain.models.TextFieldState
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.navigation.To_MAIN_GRAPH_ROUTE
import teka.android.organiks_platform_android.presentation.feature_auth.core.domain.model.LoginResult
import teka.android.organiks_platform_android.presentation.feature_auth.core.domain.model.RegistrationValidationResultModel
import teka.android.organiks_platform_android.presentation.feature_auth.core.domain.use_case.LoginDetailsValidationUseCase
import teka.android.organiks_platform_android.presentation.feature_auth.core.domain.use_case.RegistrationDetailsValidationUseCase
import teka.android.organiks_platform_android.presentation.feature_auth.core.repository.UserPreferencesKeyModel
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.LoginResultModel
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.RegistrationResult
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.models.UserRoleDto
import teka.android.organiks_platform_android.domain.repository.DataStoreRepository
import teka.android.organiks_platform_android.util.UiEvents
import teka.android.organiks_platform_android.util.data.ResultResource
import timber.log.Timber
import javax.inject.Inject

data class LoginState(
    val isLoading: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val dataStoreRepository: DataStoreRepository
    ) : ViewModel() {

    //Data from DataStore
    var isLoggedInState: Flow<Boolean> = dataStoreRepository.readLoggedInState()
    var loggedInUserName: Flow<String> = dataStoreRepository.readLoggedInUserName
    var loggedInUserPhone: Flow<String?> = dataStoreRepository.readLoggedInUserPhone
    var loggedInUserRoleId: Flow<Int> = dataStoreRepository.readLoggedInUserRoleId
    var loggedInUserKey: Flow<String> = dataStoreRepository.getAccessToken
    var loggedInUserPreferenceModel: Flow<UserPreferencesKeyModel> = dataStoreRepository.userPreferencesKeyModelFlow

    private var _possibleRoleIDs = MutableStateFlow<List<UserRoleDto>>(emptyList())
    val possibleRoleIDs: Flow<List<UserRoleDto>> = _possibleRoleIDs
    fun setPossibleRoleIDs(value: List<UserRoleDto>) {
        Log.d("POSSIBLE ROLES2", value.toString())
        _possibleRoleIDs.value = value
    }

    private var _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    private var _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _isRegistered = MutableStateFlow(false)
    val isRegistered: StateFlow<Boolean> = _isRegistered

    private val _registrationState = mutableStateOf(LoginState())
    val registrationState: State<LoginState> = _registrationState

    private val _registrationEventFlow = MutableSharedFlow<UiEvents>()
    val registrationEventFlow = _registrationEventFlow.asSharedFlow()



    private val _usernameState = mutableStateOf(TextFieldState(text = ""))
    val usernameState: State<TextFieldState> = _usernameState
    fun setUsername(value: String) {
        _usernameState.value = usernameState.value.copy(text = value)
    }


    private val _passwordState = mutableStateOf(TextFieldState(text = ""))
    val passwordState: State<TextFieldState> = _passwordState
    fun setPassword(value: String) {
        _passwordState.value = _passwordState.value.copy(text = value)
    }


    private val _rememberMeState = mutableStateOf(false)
    val rememberMeState: State<Boolean> = _rememberMeState
    fun setRememberMe(value: Boolean) {
        _rememberMeState.value = value
    }

    private val _loginEventFlow = MutableSharedFlow<UiEvents>()
    val loginEventFlow = _loginEventFlow.asSharedFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = loginState.value.copy(isLoading = true)
            val validationResult = LoginDetailsValidationUseCase()(username = username, password =  password, false)

            if (validationResult.result is ResultResource.Success<*>) {
                // Validation passed, proceed with the repository call
                performLogin(username, password, false)
            } else {
                // Validation failed, update the result
                updateLoginResult(validationResult)
                _loginState.value = _loginState.value.copy(isLoading = false)
            }
            _loginState.value = loginState.value.copy(isLoading = false)

        }
    }

    private fun updateLoginResult(result: LoginResult) {
        _usernameState.value = usernameState.value.copy(error = result.usernameError)
        _passwordState.value = passwordState.value.copy(error = result.passwordError)
    }

    private suspend fun performLogin(username: String, password: String, rememberMe: Boolean) {
        try {

            when (val result: LoginResultModel = authManager.login(username, password)) {
                is LoginResultModel.Success -> {
                    var toRoute:String = To_MAIN_GRAPH_ROUTE

                    Log.d("POSSIBLE ROLES1", result.roles.toString())

                    if (!(result.roles.isNullOrEmpty()) && result.roles.size > 1){
//                        toRoute = AppScreens.AuthRolesSelectionScreen.route
                        setPossibleRoleIDs(result.roles)
                        Log.d("POSSIBLE ROLES3", result.roles.toString())
                        dataStoreRepository.saveLoggedInState(isLoggedIn = false)
                    }else{
                        dataStoreRepository.saveLoggedInState(isLoggedIn = true)
                        dataStoreRepository.saveRoleId(1)
                        _loginEventFlow.emit(UiEvents.NavigateEvent(To_MAIN_GRAPH_ROUTE))
                    }


//                    _loginEventFlow.emit(UiEvents.NavigateEvent(To_MAIN_GRAPH_ROUTE))
//                    _loginEventFlow.emit(UiEvents.NavigateEvent(toRoute))
                }
                is LoginResultModel.Failure -> {
                    val errorMessage = result.errorMessage?.uppercase()
                    _loginEventFlow.emit(UiEvents.SnackbarEvent("Login failed::. $errorMessage!!"))
                }
            }
        } catch (e: Exception) {
            // Handle errors or exceptions from the repository call
            _loginEventFlow.emit(UiEvents.SnackbarEvent("Login failed:: ${e.localizedMessage}"))
        } finally {
            _loginState.value = _loginState.value.copy(isLoading = false)
        }
    }



    private val _registrationPhoneState = mutableStateOf(TextFieldState(text = ""))
    val registrationPhoneState: State<TextFieldState> = _registrationPhoneState
    private val _registrationEmailState = mutableStateOf(TextFieldState(text = ""))
    val registrationEmailState: State<TextFieldState> = _registrationEmailState
    private val _registrationPasswordState = mutableStateOf(TextFieldState(text = ""))
    val registrationPasswordState: State<TextFieldState> = _registrationPasswordState
    private val _registrationPasswordConfirmState = mutableStateOf(TextFieldState(text = ""))
    val registrationPasswordConfirmState: State<TextFieldState> = _registrationPasswordConfirmState


    fun setRegistrationPhone(value: String) {
        _registrationPhoneState.value = registrationPhoneState.value.copy(text = value)
    }

    fun setRegistrationEmail(value: String) {
        _registrationEmailState.value = registrationEmailState.value.copy(text = value)
    }

    fun setRegistrationPassword(value: String) {
        _registrationPasswordState.value = registrationPasswordState.value.copy(text = value)
    }

    fun setRegistrationConfirmPassword(value: String) {
        _registrationPasswordConfirmState.value = registrationPasswordConfirmState.value.copy(text = value)
    }

    fun register(
        phone: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _registrationState.value = loginState.value.copy(isLoading = true)

            val last9Digits = phone.takeLast(9)
            val formattedPhoneNumber = "254$last9Digits"

            val validationResult = RegistrationDetailsValidationUseCase()(
                phone = phone,
                email = email,
                password = password,
                passwordConfirmation = passwordConfirmation,
                false
            )

            if (validationResult.result is ResultResource.Success<*>) {
                performRegistration(
                    phone = formattedPhoneNumber,
                    email = email,
                    password = password,
                    passwordConfirmation = passwordConfirmation,
                    false
                )
            } else {
                updateRegistrationResult(validationResult)
                _registrationState.value = _loginState.value.copy(isLoading = false)
            }

            _registrationState.value = loginState.value.copy(isLoading = false)
        }
    }

    private fun updateRegistrationResult(result: RegistrationValidationResultModel) {
        _registrationPhoneState.value = registrationPhoneState.value.copy(error = result.phoneError)
        _registrationEmailState.value = registrationEmailState.value.copy(error = result.emailError)
        _registrationPasswordState.value = registrationPasswordState.value.copy(error = result.passwordError)
        _registrationPasswordConfirmState.value = registrationPasswordConfirmState.value.copy(error = result.passwordError)
    }

    private suspend fun performRegistration(
        phone: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        rememberMe: Boolean
    ) {

        val last9Digits = phone.takeLast(9)
        val formattedPhoneNumber = "254$last9Digits"

        try {
            val result: RegistrationResult<Boolean> = authManager.register(
                phone = formattedPhoneNumber,
                email = email,
                password = password,
                passwordConfirmation = passwordConfirmation
            )

            _isRegistered.value = when (result) {
                is RegistrationResult.Success -> {
                    _isRegistered.value = result.data
                    _registrationState.value = registrationState.value.copy(
                        isLoading = false
                    )
//                    if (result.data)dataStoreRepository.saveLoggedInState(isLoggedIn = result.data)
                    _registrationEventFlow.emit(UiEvents.NavigateEvent(AppScreens.Login.route))
                    result.data
                }
                is RegistrationResult.Failure -> {
                    Timber.e(result.exception, "Registration failed.")
                    val errorMessage = result.errorMessage?.uppercase()

                    _registrationEventFlow.emit(UiEvents.SnackbarEvent("Registration failed!!::.${errorMessage}"))
                    false
                }
            }
        } catch (e: Exception) {
            // Handle errors or exceptions from the repository call
            _registrationEventFlow.emit(UiEvents.SnackbarEvent("Registration failed: ${e.localizedMessage}"))
        } finally {
            _registrationState.value = registrationState.value.copy(isLoading = false)
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