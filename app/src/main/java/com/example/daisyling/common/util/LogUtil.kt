package com.example.daisyling.common.util

import android.util.Log

/**
 * Created by Emily on 9/30/21
 */
object LogUtil {
    const val mDebug = true
    private const val TAG = "DaisyLing"

    fun i(msg: String) {
        i(TAG, msg)
    }

    fun d(msg: String) {
        d(TAG, msg)
    }

    fun w(msg: String) {
        w(TAG, msg)
    }

    fun v(msg: String) {
        v(TAG, msg)
    }

    fun e(msg: String, error: ArrayList<String?>?) {
        e(TAG, msg)
    }

    fun i(tag: String?, msg: String) {
        val mess = logPrefix + msg
        if (mDebug) Log.i(tag, mess)
    }

    fun d(tag: String?, msg: String) {
        val mess = logPrefix + msg
        if (mDebug) Log.d(tag, mess)
    }

    fun w(tag: String?, msg: String) {
        val mess = logPrefix + msg
        if (mDebug) Log.w(tag, mess)
    }

    fun v(tag: String?, msg: String) {
        val mess = logPrefix + msg
        if (mDebug) Log.v(tag, mess)
    }

    fun e(tag: String?, msg: String) {
        val mess = logPrefix + msg
        if (mDebug) Log.e(tag, mess)
    }

    private val logPrefix: String
        get() {
            logId++
            if (logId >= 1000) logId = 1
            return "($logId). "
        }

    private var logId = 0

}