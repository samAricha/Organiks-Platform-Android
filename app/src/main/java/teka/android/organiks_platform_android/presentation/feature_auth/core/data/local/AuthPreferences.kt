package teka.android.organiks_platform_android.presentation.feature_auth.core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import teka.android.organiks_platform_android.presentation.feature_auth.core.data.dto.UserResponseDto
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.AuthConstants.AUTH_KEY
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.AuthConstants.USER_DATA

class AuthPreferences(private val dataStore: DataStore<Preferences>, private val gson: Gson) {

    suspend fun saveAccessToken(accessToken: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_KEY] = accessToken
        }
    }

    val getAccessToken: Flow<String> = dataStore.data.map { preferences ->
        preferences[AUTH_KEY] ?: ""
    }

    suspend fun clearAccessToken() {
        dataStore.edit { preferences ->
            preferences[AUTH_KEY] = ""
        }
    }

    suspend fun saveUserdata(user: UserResponseDto) {
        dataStore.edit { preferences ->
            preferences[USER_DATA] = gson.toJson(user)
        }
    }

    val getUserData: Flow<String> = dataStore.data.map { preferences ->
        preferences[USER_DATA] ?: ""
    }
}