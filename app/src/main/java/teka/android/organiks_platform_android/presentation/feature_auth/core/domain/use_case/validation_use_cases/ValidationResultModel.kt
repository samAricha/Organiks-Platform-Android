package teka.android.organiks_platform_android.presentation.feature_auth.core.domain.use_case.validation_use_cases

data class ValidationResultModel(
    val successful: Boolean,
    val errorMessage: String? = null
)