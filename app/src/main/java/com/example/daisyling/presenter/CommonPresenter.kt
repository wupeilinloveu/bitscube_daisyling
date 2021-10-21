package com.example.daisyling.presenter

import com.example.daisyling.common.base.BaseView

/**
 * Created by Emily on 9/30/21
 */
class CommonPresenter(baseView: BaseView) : BasePresenter(baseView) {
    /**
     * Music
     */
    fun getMusic(term: String, limit: Int, offset: Int) {
        mProtocol.getMusic(mBaseCallback, term, limit, offset)
    }

    /**
     * Video
     */
    fun getVideo(term: String, limit: Int, offset: Int) {
        mProtocol.getVideo(mBaseCallback, term, limit, offset)
    }

    /**
     * Track id
     */
    fun getTrackId(id: Long) {
        mProtocol.getTrackId(mBaseCallback, id)
    }
}