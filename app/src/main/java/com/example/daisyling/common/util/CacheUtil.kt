package com.example.daisyling.common.util

import android.app.Activity
import android.os.Environment
import java.io.File
import java.math.BigDecimal

/**
 * Created by Emily on 10/26/21
 */
class CacheUtil {
    companion object{
        val util by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
            CacheUtil()
        }
    }

    /**
     * Get cache size（MB）
     * */
    fun getCacheSize(activity: Activity):String{
        val finalSize: String
        var cacheSize = activity.cacheDir.size()
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            cacheSize += activity.externalCacheDir!!.size()
        }
        finalSize = getFormatSize(cacheSize)
        return finalSize
    }

    /**
     * Clear cache
     * */
    fun clearCache(activity: Activity){
        activity.cacheDir.clearFile()
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            activity.externalCacheDir?.clearFile()
        }
    }

    /**
     * Get format size
     * */
    private fun getFormatSize(size:Long):String{
        if (size <= 0) {
            return "0.00 KB"
        }
        val kSize = size / 1024f
        if (kSize < 1) {
            val result1 = BigDecimal(kSize.toString())
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "KB"
        }

        val mSize = kSize / 1024
        val result1 = BigDecimal(mSize.toString())
        return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
            .toPlainString() + "MB"
    }

    /**
     * Get file size
     * */
    private fun File.size():Long{
        var size = 0L
        try {
            if (isFile) {
                size += length()
            }
            if (isDirectory) {
                listFiles().forEach {
                    size += it.size()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    /**
     * Clear file
     * */
    fun File.clearFile() {
        if (isFile) delete()
        if (isDirectory) listFiles().forEach { it.clearFile() }
    }
}