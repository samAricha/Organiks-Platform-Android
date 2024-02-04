package teka.android.organiks_platform_android.domain.models

sealed class ChatStatusModel {
    object Idle : ChatStatusModel()
    class Success(val data: String) : ChatStatusModel()
    class Error(val message: String) : ChatStatusModel()
    object Loading : ChatStatusModel()
}