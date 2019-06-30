package com.app.mmse.recall.utils.old_utils

import android.content.Context

class OldSharedPrefUtils {

    private val GLOBAL_PREFERENCES = "GLOBAL_PREFERENCES"

    //Not Logged in/Logged Out -> "0"
    //Logged in -> "1"

    fun savePreferences(context: Context, key: String, value: String) {
        val sharedPreferences = context.getSharedPreferences(GLOBAL_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun loadPreferences(context: Context, key: String, defaultValue: String): String? {
        val sharedPreferences = context.getSharedPreferences(GLOBAL_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, defaultValue)
    }
}