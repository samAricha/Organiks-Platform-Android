package teka.android.organiks_platform_android.presentation.feature_ai_assistant.data
sealed class ChatStatusModel {
    data object Idle : ChatStatusModel()
    class Success(val data: String) : ChatStatusModel()
    class Error(val message: String) : ChatStatusModel()
    data object Loading : ChatStatusModel()
}