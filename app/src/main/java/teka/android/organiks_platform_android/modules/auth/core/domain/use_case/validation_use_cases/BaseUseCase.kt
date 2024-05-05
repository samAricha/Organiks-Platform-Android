package teka.android.organiks_platform_android.modules.auth.core.domain.use_case.validation_use_cases

interface BaseUseCase<In, Out> {
    fun execute(input: In): Out
}