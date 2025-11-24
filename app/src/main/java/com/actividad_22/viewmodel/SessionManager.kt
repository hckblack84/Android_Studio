package com.actividad_22.viewmodel


import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "user_session"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    fun saveUserSession(userId: Long, email: String, name: String) {
        prefs.edit().apply {
            putLong(KEY_USER_ID, userId)
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_NAME, name)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    fun getUserId(): Long {
        return prefs.getLong(KEY_USER_ID, -1)
    }

    fun getUserEmail(): String? {
        return prefs.getString(KEY_USER_EMAIL, null)
    }

    fun getUserName(): String? {
        return prefs.getString(KEY_USER_NAME, null)
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}