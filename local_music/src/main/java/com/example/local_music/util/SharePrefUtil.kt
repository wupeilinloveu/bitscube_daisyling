package com.example.local_music.util

import android.content.Context
import android.content.SharedPreferences
import org.litepal.LitePalApplication

/**
 * Created by Emily on 11/1/21
 */
object SharePrefUtil {
    const val appPref = "Local_music"

    /**
     * 保存字符串
     * @param fileName
     * @param key
     * @param value
     */
    fun saveData(fileName: String, key: String, value: String) {
        val sharedPreferences: SharedPreferences =
            LitePalApplication.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(key, value).apply()
    }

    /**
     * 获取字符串
     * @param fileName
     * @param key
     * @return
     */
    fun getData(fileName: String, key: String): String {
        val sharedPreferences: SharedPreferences =
            LitePalApplication.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "") ?: ""
    }

    /**
     * 保存int
     * @param fileName
     * @param key
     * @param value
     */
    fun saveData(fileName: String, key: String, value: Int) {
        val sharedPreferences: SharedPreferences =
            LitePalApplication.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(key, value).apply()
    }

    /**
     * 获取int
     * @param fileName
     * @param key
     * @return
     */
    fun getIntData(fileName: String, key: String): Int {
        val sharedPreferences: SharedPreferences =
            LitePalApplication.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, 0)
    }

    /**
     * 保存布尔值
     * @param fileName
     * @param key
     * @param value
     */
    fun saveData(fileName: String, key: String, value: Boolean) {
        val sharedPreferences: SharedPreferences =
            LitePalApplication.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    /**
     * 获取布尔值
     * @param fileName
     * @param key
     * @return
     */
    fun getBooleanData(fileName: String, key: String): Boolean {
        val sharedPreferences: SharedPreferences =
            LitePalApplication.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }
}