package com.example.daisyling.common.util

import com.google.gson.Gson
import com.google.gson.JsonParser

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

    /**
     * Transfer to List
     */
    fun <T> gsonToList(gsonString: String?, cls: Class<T>?): List<T> {
        val gson = Gson()
        val mList = ArrayList<T>()
        val array = JsonParser().parse(gsonString).asJsonArray
        for (elem in array) {
            mList.add(gson.fromJson(elem, cls))
        }
        return mList
    }


}