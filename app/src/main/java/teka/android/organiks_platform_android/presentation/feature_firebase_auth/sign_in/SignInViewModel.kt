package teka.android.organiks_platform_android.presentation.feature_firebase_auth.sign_in

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.sign_in.SignInResult
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.sign_in.SignInState

class SignInViewModel: ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}