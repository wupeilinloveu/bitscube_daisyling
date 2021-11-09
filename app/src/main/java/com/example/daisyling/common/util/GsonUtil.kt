package com.example.daisyling.common.util

import com.google.gson.Gson

/**
 * Created by Emily on 9/30/21
 */
object GsonUtil {
    /**
     * Transfer to Bean
     */
    fun <T> gsonToBean(gsonString: String?, cls: Class<T>?): T {
        val t: T
        val gson = Gson()
        t = gson.fromJson(gsonString, cls)
        return t
    }
}