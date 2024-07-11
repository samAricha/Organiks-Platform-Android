package teka.android.organiks_platform_android.presentation.feature_auth.core.domain.repository

import teka.android.organiks_platform_android.presentation.feature_auth.core.data.remote.request.LoginRequest
import teka.android.organiks_platform_android.util.data.ResultResource

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest, rememberMe: Boolean): ResultResource<Unit>
    suspend fun autoLogin(): ResultResource<Unit>
    suspend fun logout(): ResultResource<Unit>
}
