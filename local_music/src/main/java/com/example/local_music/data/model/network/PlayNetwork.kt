package com.example.local_music.data.model.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Emily on 11/1/21
 */
class PlayNetwork {
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    companion object {
        private var network: PlayNetwork? = null

        fun getInstance(): PlayNetwork {
            if (network == null) {
                synchronized(PlayNetwork::class.java) {
                    if (network == null) {
                        network = PlayNetwork()
                    }
                }
            }
            return network!!
        }
    }
}