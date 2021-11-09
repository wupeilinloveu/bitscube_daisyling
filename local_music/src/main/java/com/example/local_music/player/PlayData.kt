package com.example.local_music.player

import com.example.local_music.data.model.play.PlaySong

/**
 * Created by Emily on 11/1/21
 */
class PlayData {
    /**
     * Song target
     */
    var target = ""

    /**
     * Progress
     */
    var progress = 0

    /**
     * Song duration
     */
    var duration = 0

    /**
     * Play type
     */
    var playType: PlayType = PlayType.LIST

    /**
     * Play list
     */
    var playList: MutableList<PlaySong> = arrayListOf()
}