package teka.android.organiks_platform_android.modules.auth.core.domain.model

import teka.android.organiks_platform_android.util.data.ResultResource


data class LoginResult(
    var passwordError: String? = null,
    var usernameError: String? = null,
    var result: ResultResource<Unit>? = null
)
