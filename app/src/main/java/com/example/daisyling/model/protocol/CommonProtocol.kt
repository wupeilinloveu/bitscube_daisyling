package com.example.daisyling.model.protocol

import com.example.daisyling.model.bean.Track
import com.example.daisyling.model.bean.TrackId

/**
 * Created by Emily on 9/30/21
 */
class CommonProtocol : BaseProtocol() {
    /**
     * Music
     */
    fun getMusic(callback: HttpCallback, term: String, limit: Int, offset: Int) {
        super.execute(
            super.service!!.getMusic(term, limit, offset),
            callback,
            IHttpService.HTTP_GET_MUSIC,
            Track::class.java
        )
    }

    /**
     * Video
     */
    fun getVideo(callback: HttpCallback, term: String, limit: Int, offset: Int) {
        super.execute(
            super.service!!.getVideo(term, limit, offset),
            callback,
            IHttpService.HTTP_GET_VIDEO,
            Track::class.java
        )
    }

    /**
     * Track id
     */
    fun getTrackId(callback: HttpCallback, id: Long) {
        super.execute(
            super.service!!.getTrackId(id),
            callback,
            IHttpService.HTTP_GET_TRACK_ID,
            TrackId::class.java
        )
    }
}
