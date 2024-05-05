package teka.android.organiks_platform_android.modules.auth.core.domain.use_case

import teka.android.organiks_platform_android.modules.auth.core.domain.repository.LoginRepository
import teka.android.organiks_platform_android.util.data.ResultResource

class LogoutUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): ResultResource<Unit> {
        return loginRepository.logout()
    }
}
