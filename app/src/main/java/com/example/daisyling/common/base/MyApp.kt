package com.example.daisyling.common.base

import android.app.Application

/**
 * Created by Emily on 9/30/21
 */
class MyApp : Application() {
    companion object {
        var instance: MyApp? = null
        var context: Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = this
    }
}