package com.example.local_music.data

import com.example.local_music.data.db.SongDao
import com.example.local_music.data.model.play.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Emily on 11/1/21
 */
class SongRepository private constructor(private val songDao: SongDao) {
    suspend fun getSongList(songsId: Long) = withContext(Dispatchers.IO) {
        val list = songDao.getSongList(songsId)
        list
    }

    fun getLocalSongList(songsId: Long): MutableList<Song> {
        return songDao.getSongList(songsId)
    }

    fun getSong(songsId: Long, target: String): Song? {
        return songDao.getSong(songsId, target)
    }

    fun getSong(id: Long): Song? {
        return songDao.getSong(id)
    }

    fun delete(id: Long): Boolean {
        return songDao.delete(id)
    }

    fun addSong(song: Song): Boolean {
        return songDao.addSong(song)
    }

    fun edit(song: Song): Boolean {
        return songDao.update(song)
    }

    fun deleteSong(song: Song): Boolean {
        return songDao.deleteSong(song)
    }

    companion object {
        private var instance: SongRepository? = null
        fun getInstance(songDao: SongDao): SongRepository {
            if (instance == null) {
                synchronized(SongRepository::class.java) {
                    if (instance == null) {
                        instance = SongRepository(songDao)
                    }
                }
            }
            return instance!!
        }
    }
}