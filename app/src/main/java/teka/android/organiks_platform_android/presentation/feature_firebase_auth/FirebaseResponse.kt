package teka.android.organiks_platform_android.presentation.feature_firebase_auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.StateFlow

typealias FirebaseSignInResponse = Response<AuthResult>
typealias SignOutResponse = Response<Boolean>
typealias AuthStateResponse = StateFlow<FirebaseUser?>

sealed class Response<out T> {
    object Loading: Response<Nothing>()
    data class Success<out T>(val data: T?): Response<T>()
    data class Failure(val e: Exception): Response<Nothing>()
}