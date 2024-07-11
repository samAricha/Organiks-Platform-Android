package teka.android.organiks_platform_android.presentation.feature_auth.registration;

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import teka.android.organiks_platform_android.domain.form.validation.UiText
import teka.android.organiks_platform_android.domain.form.validation.use_cases.ValidateEmailUseCase
import teka.android.organiks_platform_android.domain.form.validation.use_cases.ValidatePasswordUseCase
import teka.android.organiks_platform_android.domain.form.validation.use_cases.ValidatePhoneNumberUseCase
import teka.android.organiks_platform_android.repository.DataStoreRepository
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: DataStoreRepository,
) : ViewModel() {


    private val validateEmailUseCase = ValidateEmailUseCase()
    private val validatePasswordUseCase = ValidatePasswordUseCase()
    private val validatePhoneNumberUseCase = ValidatePhoneNumberUseCase()

    var formState by mutableStateOf(MainState())


    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
                validateEmail()
            }

            is MainEvent.PhoneNumberChanged -> {
                formState = formState.copy(phoneNumber = event.phoneNumber)
                validatePhoneNumber()
            }

            is MainEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
                validatePassword()
            }

            is MainEvent.PasswordConfirmationChanged -> {
                formState = formState.copy(passwordConfirmation = event.passwordConfirmation)
                validatePasswordConfirmation()
            }

            is MainEvent.VisiblePassword -> {
                formState = formState.copy(isVisiblePassword = event.isVisiblePassword)
            }

            is MainEvent.Submit -> {
                if (validateEmail() && validatePassword()) {
//                    login()
                }
            }

            else -> {}
        }
    }




    private fun validateEmail(): Boolean {
        val emailResult = validateEmailUseCase.execute(formState.email)
        formState = formState.copy(emailError = emailResult.errorMessage)
        return emailResult.successful
    }
    private fun validatePassword(): Boolean {
        val passwordResult = validatePasswordUseCase.execute(formState.password)
        formState = formState.copy(passwordError = passwordResult.errorMessage)
        return passwordResult.successful
    }
    private fun validatePasswordConfirmation(): Boolean {
        val passwordConfirmationResult = validatePasswordUseCase.execute(formState.passwordConfirmation)
        formState = formState.copy(passwordConfirmationError = passwordConfirmationResult.errorMessage)
        return passwordConfirmationResult.successful
    }

    private fun validatePhoneNumber(): Boolean {
        val phoneNumberResult = validatePhoneNumberUseCase.execute(formState.phoneNumber)
        formState = formState.copy(phoneNumberError = phoneNumberResult.errorMessage)
        return phoneNumberResult.successful
    }

}


sealed class MainEvent {
    data class EmailChanged(val email: String) : MainEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : MainEvent()
    data class PasswordChanged(val password: String) : MainEvent()
    data class PasswordConfirmationChanged(val passwordConfirmation: String) : MainEvent()
    data class VisiblePassword(val isVisiblePassword: Boolean) : MainEvent()
    object Submit : MainEvent()
}

data class MainState(
    val email: String = "",
    val emailError: UiText? = null,
    val phoneNumber: String = "",
    val phoneNumberError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val passwordConfirmation: String = "",
    val passwordConfirmationError: UiText? = null,
    val isVisiblePassword: Boolean = false
)
