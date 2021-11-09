package com.example.local_music.util

/**
 * Created by Emily on 11/1/21
 */
object ArrayUtil {
    fun getEnableIndex(index: Int, n: Int): Int {
        return 0.coerceAtLeast(index).coerceAtMost(n - 1)
    }

    fun <T> toString(a: Array<T>?): String {
        if (a == null) {
            return ""
        }
        val sb = StringBuilder("[")
        for (t in a) {
            sb.append(t).append(",")
        }
        sb.append("]")
        return sb.toString()
    }

    fun toString(a: IntArray?): String {
        if (a == null) {
            return ""
        }
        val na = arrayOfNulls<Int>(a.size)
        for (i in a.indices) {
            na[i] = a[i]
        }
        return toString(na)
    }
}