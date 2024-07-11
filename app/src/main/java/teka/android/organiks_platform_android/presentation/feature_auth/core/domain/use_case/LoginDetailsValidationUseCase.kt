package teka.android.organiks_platform_android.presentation.feature_auth.core.domain.use_case

import android.util.Patterns
import teka.android.organiks_platform_android.presentation.feature_auth.core.domain.model.LoginResult
import teka.android.organiks_platform_android.util.data.ResultResource

class LoginDetailsValidationUseCase() {
    suspend operator fun invoke(
        username: String,
        password: String,
        rememberMe: Boolean
    ): LoginResult {
        // this fun use for check if value is number
        fun isNumber(value: String): Boolean {
            return value.isEmpty() || Regex("^\\d+\$").matches(value)
        }

        fun isEmailValid(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isPasswordValid(password: String): Boolean {
            return password.any { it.isDigit() } &&
                    password.any { it.isLetter() }
        }


        val usernameError = if (username.isBlank()) "Username cannot be blank" else null
        val passwordError = if (password.isBlank()) "Password cannot be blank" else null

        if (usernameError != null || passwordError != null) {
            return LoginResult(
                usernameError = usernameError,
                passwordError = passwordError
            )
        }

        // Simulate a successful login result without making an actual repository call
        return LoginResult(result = ResultResource.Success(Unit))
    }
}