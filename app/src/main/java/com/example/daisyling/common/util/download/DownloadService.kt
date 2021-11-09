package com.example.daisyling.common.util.download

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by Emily on 10/11/21
 */
interface DownloadService {
    @Streaming
    @GET
    fun downloadWithDynamicUrl(@Url fileUrl: String?): Call<ResponseBody?>?
}