package com.example.daisyling.common.util.download

import java.io.File

/**
 * Created by Emily on 10/11/21
 */
interface DownloadListener {
    fun onFinish(file: File?)
    fun onProgress(progress: Int, downloadedLengthKb: Long, totalLengthKb: Long)
    fun onFailed(errMsg: String?)
}