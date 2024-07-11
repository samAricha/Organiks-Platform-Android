package teka.android.organiks_platform_android.presentation.feature_auth.core.domain.use_case

import teka.android.organiks_platform_android.presentation.feature_auth.core.data.remote.request.LoginRequest
import teka.android.organiks_platform_android.presentation.feature_auth.core.domain.model.LoginResult
import teka.android.organiks_platform_android.presentation.feature_auth.core.domain.repository.LoginRepository


class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        username: String,
        password: String,
        rememberMe: Boolean
    ): LoginResult {
        val usernameError = if (username.isBlank()) "User name cannot be blank" else null
        val passwordError = if (password.isBlank()) "Password cannot be blank" else null


        if (usernameError != null) {
            return LoginResult(
                usernameError = usernameError
            )
        }

        if (passwordError != null) {
            return LoginResult(
                passwordError = passwordError
            )
        }


        val loginRequest = LoginRequest(
            username = username.trim(),
            password = password.trim()
        )

        return LoginResult(
            result = loginRepository.login(loginRequest, rememberMe)
        )
    }
}