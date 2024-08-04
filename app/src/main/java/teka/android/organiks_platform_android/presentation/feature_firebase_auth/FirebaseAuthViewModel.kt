package teka.android.organiks_platform_android.presentation.feature_firebase_auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirebaseAuthViewModel @Inject constructor(
    private val repository: FirebaseAuthRepository
): ViewModel() {
    val currentUser = getAuthState()

    init {
        getAuthState()
    }

    private fun getAuthState() = repository.getAuthState(viewModelScope)


    fun signOut() = CoroutineScope(Dispatchers.IO).launch {
        repository.signOut()
    }
}