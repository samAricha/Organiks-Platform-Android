package teka.android.organiks_platform_android.presentation.feature_firebase_auth

import kotlinx.coroutines.CoroutineScope

interface FirebaseAuthRepository {

    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse

    suspend fun signInAnonymously(): FirebaseSignInResponse

    suspend fun signOut(): SignOutResponse
}