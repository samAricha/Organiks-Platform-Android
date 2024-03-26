package teka.android.organiks_platform_android.modules.auth.core.util.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserRoleDto(
    val id: Int,
    val name: String,
    @SerializedName("guard_name")
    val guardName: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val pivot: Pivot
)

@Serializable
data class Pivot(
    @SerializedName("model_type")
    val modelType: String,
    @SerializedName("model_id")
    val modelId: Int,
    @SerializedName("role_id")
    val roleId: Int
)