package com.example.daisyling.common.util.download

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Emily on 10/11/21
 */
class DownloadInterceptor(private val listener: DownloadListener) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .body(DownloadResponseBody(originalResponse.body!!, listener))
            .build()
    }
}