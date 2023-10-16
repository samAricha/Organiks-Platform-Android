package teka.android.organiks_platform_android.domain.form.models

import teka.android.organiks_platform_android.domain.form.validation.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)