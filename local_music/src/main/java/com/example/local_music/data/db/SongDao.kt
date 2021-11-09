package com.example.local_music.data.db

import com.example.local_music.data.model.play.Song
import org.litepal.LitePal

/**
 * Created by Emily on 11/1/21
 */
class SongDao {
    fun getSongList(songsId: Long): MutableList<Song> {
        return LitePal.where("songs_id=?", songsId.toString()).find(Song::class.java)
    }

    fun getSong(songsId: Long, target: String): Song? {
        val list = LitePal.where("songs_id=? and target = ?", songsId.toString(), target)
            .find(Song::class.java)
        if (list.isEmpty()) {
            return null
        }
        return list[0]
    }

    fun getSong(id: Long): Song? {
        return LitePal.find(Song::class.java, id)
    }

    fun addSong(song: Song): Boolean {
        return song.save()
    }

    fun update(song: Song): Boolean {
        return song.update(song.id) > 0
    }

    fun delete(id: Long): Boolean {
        return LitePal.find(Song::class.java, id).delete() > 0
    }

    fun deleteSong(song: Song): Boolean {
        return LitePal.find(Song::class.java, song.id).delete() > 0
    }
}