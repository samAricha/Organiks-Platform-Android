package teka.android.organiks_platform_android.presentation.feature_auth.core.domain.model

import teka.android.organiks_platform_android.presentation.feature_auth.core.data.dto.Address
import teka.android.organiks_platform_android.presentation.feature_auth.core.data.dto.Name


data class User(
    val address: Address,
    val email: String,
    val id: Int,
    val name: Name,
    val password: String,
    val phone: String,
    val username: String,
)
