package com.example.daisyling.common.util.download

/**
 * Created by Emily on 10/11/21
 */
class InputParameter private constructor(builder: Builder) {
    val baseUrl: String = builder.baseUrl
    val relativeUrl: String = builder.relativeUrl
    val loadedFilePath: String = builder.loadedFilePath
    val isCallbackOnUiThread: Boolean

    class Builder(var baseUrl: String, var relativeUrl: String, var loadedFilePath: String) {
        var isCallbackOnUiThread = false

        /**
         * Callback on UI thread
         */
        fun setCallbackOnUiThread(callbackOnUiThread: Boolean): Builder {
            isCallbackOnUiThread = callbackOnUiThread
            return this
        }

        fun build(): InputParameter {
            return InputParameter(this)
        }
    }

    init {
        isCallbackOnUiThread = builder.isCallbackOnUiThread
    }
}