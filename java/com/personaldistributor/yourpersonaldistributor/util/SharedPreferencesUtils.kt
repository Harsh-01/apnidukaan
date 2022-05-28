package com.personaldistributor.yourpersonaldistributor.util

import android.content.Context

object SharedPreferencesUtils {
    private const val SHARED_PREFS = "personal_pref"

    // Save string value
    fun saveStringToUserDefaults(context: Context?, key: String?, value: String?) {
        if (context == null) {
            return
        }
        val preferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    // get string value
    fun getStringFromUserDefaults(context: Context?, key: String?): String? {
        if (context == null) {
            return ""
        }
        val preferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        return preferences.getString(key, "")
    }

    // Save boolean value
    fun saveBooleanToUserDefaults(context: Context?, key: String?, value: Boolean) {
        if (context == null) {
            return
        }
        val preferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    // get boolean value
    fun getBooleanFromUserDefaults(context: Context?, key: String?): Boolean {
        if (context == null) {
            return false
        }
        val preferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        return preferences.getBoolean(key, false)
    }

    fun removeStringToUserDefaults(context: Context?, key: String?) {
        if (context == null) {
            return
        }
        val preferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        preferences.edit().remove(key).commit()
    }

    fun removeAllUserDefaults(context: Context?) {
        if (context == null) {
            return
        }
        val preferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        preferences.edit().clear().commit();
    }
}





