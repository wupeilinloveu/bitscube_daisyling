package com.example.daisyling.common.util.download

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

/**
 * Created by Emily on 10/11/21
 */
class MainThreadExecutor : Executor {
    private val handler = Handler(Looper.getMainLooper())
    override fun execute(r: Runnable) {
        handler.post(r)
    }
}