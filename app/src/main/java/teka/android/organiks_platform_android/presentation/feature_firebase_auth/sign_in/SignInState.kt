package teka.android.organiks_platform_android.presentation.feature_firebase_auth.sign_in

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
