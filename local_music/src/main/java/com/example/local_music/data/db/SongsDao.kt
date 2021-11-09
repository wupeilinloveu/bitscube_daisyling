package com.example.local_music.data.db

import com.example.local_music.data.model.play.Songs
import org.litepal.LitePal

/**
 * Created by Emily on 11/1/21
 */
class SongsDao {
    fun getSongsList(): MutableList<Songs> = LitePal.findAll(Songs::class.java)

    fun saveSongsList(songsList: List<Songs>?) {
        if (songsList != null && songsList.isNotEmpty()) {
            LitePal.saveAll(songsList)
        }
    }

    fun getSongs(songsId: Long): Songs? {
        return LitePal.find(Songs::class.java, songsId)
    }

    fun addSongs(songs: Songs): Boolean {
        return songs.save()
    }

    fun update(songs: Songs): Boolean {
        return songs.update(songs.id) > 0
    }

    fun delete(id: Long): Int {
        return LitePal.find(Songs::class.java, id).delete()
    }
}