package com.teamapface.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeUtils {
    private const val PREFS_NAME = "theme_prefs"
    private const val PREF_KEY_THEME = "pref_key_theme"

    fun applyTheme(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean(PREF_KEY_THEME, false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun saveThemePreference(context: Context, isDarkMode: Boolean) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(PREF_KEY_THEME, isDarkMode).apply()
    }

    fun isDarkMode(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(PREF_KEY_THEME, false)
    }
}
