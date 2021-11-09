package com.example.local_music.util

import android.util.Log

/**
 * Created by Emily on 11/1/21
 */
object Logger {
    private const val TAG = "Local_music"

    private var DEBUG_ON = true

    fun d(msg: String?) {
        if (DEBUG_ON) {
            if (msg != null) {
                Log.d(TAG, msg)
            }
        }
    }

    fun e(msg: String?) {
        if (DEBUG_ON) {
            if (msg != null) {
                Log.e(TAG, msg)
            }
        }
    }

    fun e(e: Throwable?) {
        e("", e)
    }

    fun e(msg: String?, e: Throwable?) {
        if (DEBUG_ON) {
            Log.e(TAG, msg, e)
        }
    }
}