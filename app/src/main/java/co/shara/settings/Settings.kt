package co.shara.settings

import android.content.SharedPreferences
import androidx.core.content.edit

class Settings(
    private val settings: SharedPreferences
) {

    fun getBearerToken(): String? {
        return settings.getString(SettingsConstants.BEARER_TOKEN_KEY, "")
    }

    fun setBearerToken(bearer_token: String) {
        settings.edit {
            putString(SettingsConstants.BEARER_TOKEN_KEY, bearer_token)
        }
    }

    fun isLoggedIn(): Boolean {
        return settings.getBoolean(SettingsConstants.LOGGED_IN_KEY, false)
    }

    fun setIsLoggedIn(is_logged_in: Boolean) {
        settings.edit {
            putBoolean(SettingsConstants.LOGGED_IN_KEY, is_logged_in)
        }
    }

    fun clearData() {
        settings.edit { clear() }
    }
}

object SettingsConstants {
    const val BEARER_TOKEN_KEY = "bearer_token"
    const val USER_ID_KEY = "user_id"
    const val LOGGED_IN_KEY = "is_logged_in"
}