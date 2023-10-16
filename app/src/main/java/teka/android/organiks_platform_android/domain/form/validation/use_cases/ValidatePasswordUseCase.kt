package teka.android.organiks_platform_android.domain.form.validation.use_cases

import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.domain.form.functions.isPasswordValid
import teka.android.organiks_platform_android.domain.form.models.ValidationResult
import teka.android.organiks_platform_android.domain.form.validation.UiText


class ValidatePasswordUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (input.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePasswordNeedsToConsistOfAtLeastEightCharacters),
            )
        }

        if (!isPasswordValid(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePasswordNeedsToContainAtLeastOneLetterAndDigit),
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}