package com.example.daisyling.model.protocol

import android.os.Message
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by Emily on 9/30/21
 */
open class BaseProtocol {
    val service: IHttpService? = RetrofitManager.instance.service

    /**
     * Execution of network requests
     * @param call
     * @param callback
     * @param reqType Interface requested type
     * @param clazz Json object
     * @param what Differentiating different network requests
     */
    private fun <T> execute(
        call: Call<JsonObject>,
        callback: HttpCallback?,
        reqType: Int,
        clazz: Class<T>,
        what: Int
    ) {
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val code = response.code()
                try {
                    //Request succeeded
                    if (code == 200) {
                        val msg = Message()
                        val json = response.body().toString()
                        msg.obj = json
                        msg.what = what
                        callback?.onHttpSuccess(reqType, msg)
                    } else {
                        //Request failed
                        val jsonObject =
                            JSONObject(Objects.requireNonNull(response.errorBody())?.string())
                        val msg = jsonObject.getString("msg")
                        callback?.onHttpError(reqType, msg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return
                }
            }

            override fun onFailure(call: Call<JsonObject>, e: Throwable) {
            }
        })
    }

    fun <T> execute(
        call: Call<JsonObject>,
        callback: HttpCallback,
        reqType: Int,
        clazz: Class<T>
    ) {
        execute(call, callback, reqType, clazz, 0)
    }

    /** Network request callback interface. */
    interface HttpCallback {
        /**
         * Request succeeded
         * @param reqType Interface requested type
         * @param msg javabean object
         */
        fun onHttpSuccess(reqType: Int, msg: Message)

        /**
         * Request failed
         * @param reqType Interface requested type
         */
        fun onHttpError(reqType: Int)

        /**
         * Request failed
         * @param reqType Interface requested type
         * @param error Failure reason
         */
        fun onHttpError(reqType: Int, error: String)

        /**
         * Request timeout
         * @param reqType Interface requested type
         * @param error Failure reason
         */
        fun onHttpFailure(reqType: Int, error: String)

        /**
         * Request server failed
         * @param reqType Interface requested type
         */
        fun onServiceError(reqType: Int)

    }
}

