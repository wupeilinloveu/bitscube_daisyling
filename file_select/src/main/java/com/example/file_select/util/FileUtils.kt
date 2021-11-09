package com.example.file_select.util

/**
 * 事件点击工具类，设置事件点击的响应时间间隔
 */
object FileUtils {

    fun filter(type: String, list: List<String>?): Boolean {
        if (list == null) return true
        for (f in list) {
            if (type.equals(f, true)) {
                return true
            }
        }
        return false
    }

    fun pathGetType(path: String?): String {
        val s = path?.split("\\.".toRegex())?.toTypedArray()
        if (s != null) {
            return s[s.size - 1]
        }
        return ""
    }

    fun pathGetName(path: String?): String {
        val s = path?.split("/".toRegex())?.toTypedArray()
        if (s != null) {
            return s[s.size - 1]
        }
        return ""
    }
}