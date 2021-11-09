package com.example.daisyling.model.protocol

import com.example.daisyling.common.base.MyApp
import com.example.daisyling.common.util.LogUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * Created by Emily on 9/30/21
 */
class RetrofitManager
private constructor() {
    var retrofit: Retrofit? = null
        private set
    var service: IHttpService? = null
        private set

    private fun initRetrofit() {

        val client = OkHttpClient.Builder()

        if (LogUtil.mDebug) {
            val interceptor = HttpLoggingInterceptor()
            // Print request header and returned data
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            // Add network cache
            client.addNetworkInterceptor(interceptor)
                .callTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                .cache(provideCache())
        }

        retrofit = Retrofit.Builder()
            .baseUrl(IHttpService.HOST_URL)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
        service = retrofit!!.create(IHttpService::class.java)
    }

    init {
        initRetrofit()
    }

    private fun provideCache(): Cache? {
        var cache: Cache? = null
        try {
            // preserve cached data
            cache = Cache(
                File(MyApp.context!!.externalCacheDir, "http-cache"),
                200 * 1024 * 1024
            ) // 200 MB
        } catch (e: Exception) {
//            Log.e("cache", "Could not create Cache!");
        }
        return cache
    }

    private object SingleHolder {
        var instance = RetrofitManager()
    }

    companion object {
        val instance: RetrofitManager
            get() = SingleHolder.instance
    }
}
