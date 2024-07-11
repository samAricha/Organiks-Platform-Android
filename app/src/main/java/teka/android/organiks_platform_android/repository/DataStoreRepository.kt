package teka.android.organiks_platform_android.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import teka.android.organiks_platform_android.presentation.feature_auth.core.repository.UserPreferencesKeyModel
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.models.LoggedInUser
import java.io.IOException

val Context.onBoardingDataStore: DataStore<Preferences> by preferencesDataStore(name = "on_boarding_pref")
val Context.loggedInDataStore: DataStore<Preferences> by preferencesDataStore(name = "logged_in_pref")


class DataStoreRepository(context: Context) {

    private val onBoardingDataStore = context.onBoardingDataStore
    private val loggedInDataStore = context.loggedInDataStore


    private object PreferencesKey {
        val USER_TOKEN_KEY = stringPreferencesKey(name ="user_token")
        val onBoardingKey = booleanPreferencesKey(name = "on_boarding_completed")
        val isLoggedInKey = booleanPreferencesKey(name = "is_logged_in")
        //user data
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_PHONE_KEY = stringPreferencesKey("user_phone")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val ROLE_ID = intPreferencesKey("role_id")
        val ROLE_TYPE = intPreferencesKey("role_type")
    }



    val userPreferencesKeyModelFlow: Flow<UserPreferencesKeyModel> = loggedInDataStore.data.map { preferences ->
        val userPhone = preferences[PreferencesKey.USER_PHONE_KEY]?:""
        val userName = preferences[PreferencesKey.USER_NAME_KEY]?:""
        val userRoleId = preferences[PreferencesKey.ROLE_ID]?:0

        UserPreferencesKeyModel(userPhone = userPhone, username = userName, userRoleId = userRoleId)
    }


    val getAccessToken: Flow<String> = loggedInDataStore.data.map { preferences ->
        preferences[PreferencesKey.USER_TOKEN_KEY] ?: ""
    }

    val readLoggedInUserPhone: Flow<String?> = loggedInDataStore.data.map { preferences ->
        preferences[PreferencesKey.USER_PHONE_KEY]
    }

    val readLoggedInUserName: Flow<String> = loggedInDataStore.data.map { preferences ->
        preferences[PreferencesKey.USER_NAME_KEY] ?: ""
    }

    val readLoggedInUserRoleId: Flow<Int> = loggedInDataStore.data.map { preferences ->
        preferences[PreferencesKey.ROLE_ID] ?: 0
    }

    suspend fun saveToken(token: String) {
        loggedInDataStore.edit { preferences ->
            preferences[PreferencesKey.USER_TOKEN_KEY] = token
        }
    }

    suspend fun saveRoleId(roleId: Int) {
        loggedInDataStore.edit { preferences ->
            preferences[PreferencesKey.ROLE_ID] = roleId
        }
    }

    suspend fun saveUserData(userData: LoggedInUser) {
        loggedInDataStore.edit { preferences ->
            preferences[PreferencesKey.ROLE_ID] = userData.roles.first().pivot.roleId
            preferences[PreferencesKey.USER_NAME_KEY] = userData.name
            preferences[PreferencesKey.USER_PHONE_KEY] = userData.phone
            preferences[PreferencesKey.USER_EMAIL] = userData.email?:""
        }
    }

    suspend fun clearUserData() {
        loggedInDataStore.edit { preferences ->
            preferences.remove(PreferencesKey.ROLE_ID)
            preferences.remove(PreferencesKey.USER_NAME_KEY)
            preferences.remove(PreferencesKey.USER_PHONE_KEY)
        }
    }

    suspend fun saveOnBoardingState(completed: Boolean) {
        onBoardingDataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    suspend fun saveLoggedInState(isLoggedIn: Boolean) {
        loggedInDataStore.edit { preferences ->
            preferences[PreferencesKey.isLoggedInKey] = isLoggedIn
        }
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return onBoardingDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
                onBoardingState
            }
    }


    fun readLoggedInState(): Flow<Boolean> {
        return loggedInDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val isLoggedIn = preferences[PreferencesKey.isLoggedInKey] ?: false
                isLoggedIn
            }
    }

}