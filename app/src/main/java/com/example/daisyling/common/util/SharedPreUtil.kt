package com.example.daisyling.common.util

import android.content.Context

/**
 * Created by Emily on 9/30/21
 */
object SharedPreUtil {
    // file name.xml
    private const val CONFIG_FILE_NAME = "config"
    fun getString(context: Context, key: String?, defValue: String?): String {
        // file name .xml
        val sp = context.getSharedPreferences(
            CONFIG_FILE_NAME,
            Context.MODE_PRIVATE
        ) // Avoid file tampering
        return sp.getString(key, defValue).toString()
    }

    fun saveString(context: Context, key: String?, value: String?) {
        val sp = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getBoolean(context: Context, key: String?, defValue: Boolean): Boolean {
        val sp = context.getSharedPreferences(
            CONFIG_FILE_NAME, Context.MODE_PRIVATE
        )
        return sp.getBoolean(key, defValue)
    }

    fun saveBoolean(context: Context, key: String?, value: Boolean?) {
        val sp = context.getSharedPreferences( //
            CONFIG_FILE_NAME, Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        editor.putBoolean(key, value!!)
        editor.apply()
    }

    fun getInt(context: Context, key: String?, defValue: Int): Int {
        val sp = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        return sp.getInt(key, defValue)
    }

    fun saveInt(context: Context, key: String?, value: Int) {
        val sp = context.getSharedPreferences(
            CONFIG_FILE_NAME, Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getLong(context: Context, key: String?, defValue: Long?): Long {
        val sp = context.getSharedPreferences(
            CONFIG_FILE_NAME, Context.MODE_PRIVATE
        )
        return sp.getLong(key, defValue!!)
    }

    fun saveLong(context: Context, key: String?, value: Long?) {
        val sp = context.getSharedPreferences(
            CONFIG_FILE_NAME, Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        editor.putLong(key, value!!)
        editor.apply()
    }
}