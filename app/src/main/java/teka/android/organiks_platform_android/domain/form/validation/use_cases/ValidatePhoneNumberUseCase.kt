package teka.android.organiks_platform_android.domain.form.validation.use_cases

import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.domain.form.functions.isNumber
import teka.android.organiks_platform_android.domain.form.models.ValidationResult
import teka.android.organiks_platform_android.domain.form.validation.UiText


class ValidatePhoneNumberUseCase : BaseUseCase<String, ValidationResult> {

    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePhoneCanNotBeBlank),
            )
        }

        if (!isNumber(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePhoneNumberShouldBeContentJustDigit),
            )
        }

        if (input.length != 10) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePhoneMustBeEqualTo10),
            )
        }

        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}