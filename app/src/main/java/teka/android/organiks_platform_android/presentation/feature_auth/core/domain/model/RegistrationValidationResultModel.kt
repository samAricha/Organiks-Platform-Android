package teka.android.organiks_platform_android.presentation.feature_auth.core.domain.model

import teka.android.organiks_platform_android.util.data.ResultResource


data class RegistrationValidationResultModel(
    var phoneError: String? = null,
    var emailError: String? = null,
    var passwordError: String? = null,
    var passwordConfirmationError: String? = null,
    var result: ResultResource<Unit>? = null
)
