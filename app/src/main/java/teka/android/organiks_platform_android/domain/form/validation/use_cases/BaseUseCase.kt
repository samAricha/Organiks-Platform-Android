package teka.android.organiks_platform_android.domain.form.validation.use_cases

interface BaseUseCase<In, Out> {
    fun execute(input: In): Out
}