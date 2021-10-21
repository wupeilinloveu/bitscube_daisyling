package com.example.daisyling.model.protocol

import com.example.daisyling.common.base.Const
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Emily on 9/30/21
 */
interface IHttpService {
    companion object {
        var HOST_URL = "https://" + Const.HOST_IP

        //Music
        const val GET_MUSIC = "/search?media=music&entity=song"

        //video
        const val GET_VIDEO = "/search?media=video&entity=movie"

        //track id
        const val GET_TRACK_ID = "/lookup"


        const val HTTP_GET_MUSIC = 11
        const val HTTP_GET_VIDEO = 12
        const val HTTP_GET_TRACK_ID = 13
    }

    /**
     * Music
     */
    @GET(GET_MUSIC)
    fun getMusic(
        @Query("term") term: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<JsonObject>

    /**
     * Video
     */
    @GET(GET_VIDEO)
    fun getVideo(
        @Query("term") term: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<JsonObject>

    /**
     * TRACK ID
     */
    @GET(GET_TRACK_ID)
    fun getTrackId(
        @Query("id") id: Long,
    ): Call<JsonObject>
}