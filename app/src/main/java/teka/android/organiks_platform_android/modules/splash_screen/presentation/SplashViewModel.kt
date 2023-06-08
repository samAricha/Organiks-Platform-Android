package teka.android.organiks_platform_android.modules.splash_screen.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.modules.splash_screen.DataStoreRepository
import teka.android.organiks_platform_android.navigation.Routes
import teka.android.organiks_platform_android.navigation.Screen
import javax.inject.Inject


class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Routes.LoginScreen.name)
    val startDestination: State<String> = _startDestination

    init {


        viewModelScope.launch {
            repository.readOnBoardingState().collect { completed ->
                if (completed) {
                    _startDestination.value = Routes.LoginScreen.name
                } else {
                    _startDestination.value = Screen.Welcome.route
                }
            }
            _isLoading.value = false
        }
    }

}