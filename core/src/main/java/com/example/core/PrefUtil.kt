package com.example.core
import android.content.Context
import android.content.SharedPreferences

class PrefUtil {
    companion object {
        private fun getPreference(context: Context): SharedPreferences {
            return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        }

        fun savePreference(context: Context, key: String, value: Int) {
            val editor = getPreference(context).edit()
            editor.putInt(key, value)
            editor.apply()
        }

        fun savePreference(context: Context, key: String, value: String) {
            val editor = getPreference(context).edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun savePreference(context: Context, key: String, value: Boolean) {
            val editor = getPreference(context).edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        fun getSharePreference(context: Context, key: String, defVal: Int): Int {
            return getPreference(context).getInt(key, defVal)
        }

        fun getSharePreference(context: Context, key: String, defVal: String): String? {
            return getPreference(context).getString(key, defVal)
        }

        fun getSharePreference(context: Context, key: String, defVal: Boolean): Boolean {
            return getPreference(context).getBoolean(key, defVal)
        }
    }
}