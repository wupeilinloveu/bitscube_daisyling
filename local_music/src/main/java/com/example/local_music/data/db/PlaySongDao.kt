package com.example.local_music.data.db

import com.example.local_music.data.model.play.PlaySong
import org.litepal.LitePal

/**
 * Created by Emily on 11/1/21
 */
class PlaySongDao {
    fun getSongList(): MutableList<PlaySong> {
        return LitePal.findAll(PlaySong::class.java)
    }

    fun saveSongList(songList: List<PlaySong>) {
        LitePal.saveAll(songList)
    }

    fun getSong(target: String): PlaySong? {
        val list = LitePal.where("target=? ", target)
            .find(PlaySong::class.java)
        if (list.isEmpty()) {
            return null
        }
        return list[0]
    }
}