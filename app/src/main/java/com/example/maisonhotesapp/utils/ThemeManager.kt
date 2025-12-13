package com.example.maisonhotesapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_DARK_MODE = "dark_mode"
    private const val KEY_AUTO_MODE = "auto_mode"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun isDarkModeEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_DARK_MODE, false)
    }

    fun setDarkMode(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_DARK_MODE, enabled).apply()
        applyTheme(context)
    }

    fun isAutoModeEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_AUTO_MODE, true)
    }

    fun setAutoMode(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_AUTO_MODE, enabled).apply()
        applyTheme(context)
    }

    fun applyTheme(context: Context) {
        when {
            isAutoModeEnabled(context) -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            isDarkModeEnabled(context) -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}

