package teka.android.organiks_platform_android.presentation.feature_auth.core.util

import androidx.datastore.preferences.core.stringPreferencesKey

object AuthConstants {
    const val SPLASH_SCREEN_DURATION = 1000L
    val AUTH_KEY = stringPreferencesKey(name = "auth_key")
    const val AUTH_PREFERENCES = "AUTH_PREFERENCES"
    val USER_DATA = stringPreferencesKey("user_data")
}