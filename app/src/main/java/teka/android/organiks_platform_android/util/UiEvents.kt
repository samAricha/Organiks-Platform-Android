package teka.android.organiks_platform_android.util

sealed class UiEvents {
    data class SnackbarEvent(val message: String) : UiEvents()
    data class NavigateEvent(val route: String) : UiEvents()
}