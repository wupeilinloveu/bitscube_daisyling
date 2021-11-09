package com.example.local_music.data

import com.example.local_music.R
import com.example.local_music.data.constant.ConstantParam
import com.example.local_music.data.db.SongsDao
import com.example.local_music.data.model.play.Songs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.litepal.LitePalApplication

/**
 * Created by Emily on 11/1/21
 */
class SongsRepository private constructor(private val songsDao: SongsDao) {
    suspend fun getSongsList() = withContext(Dispatchers.IO) {
        var list = songsDao.getSongsList()
        if (list.isEmpty()) {
            // Save system songs
            list.add(
                Songs(
                    ConstantParam.SONGS_ID_LOCAL,
                    LitePalApplication.getContext().getString(R.string.lm_songs_list_local),
                    ConstantParam.SONGS_TYPE_LOCAL
                )
            )
            list.add(
                Songs(
                    ConstantParam.SONGS_ID_MARK,
                    LitePalApplication.getContext().getString(R.string.lm_songs_list_mark),
                    ConstantParam.SONGS_TYPE_MARK
                )
            )
            songsDao.saveSongsList(list)
        }
        list
    }

    fun getLocalSongsList(): MutableList<Songs> {
        return songsDao.getSongsList()
    }

    fun addSongs(songs: Songs): Boolean {
        return songsDao.addSongs(songs)
    }

    fun getSongs(songsId: Long): Songs? {
        return songsDao.getSongs(songsId)
    }

    fun edit(songs: Songs): Boolean {
        return songsDao.update(songs)
    }

    fun delete(songsId: Long): Int {
        return songsDao.delete(songsId)
    }

    companion object {
        private var instance: SongsRepository? = null
        fun getInstance(playDao: SongsDao): SongsRepository {
            if (instance == null) {
                synchronized(SongsRepository::class.java) {
                    if (instance == null) {
                        instance = SongsRepository(playDao)
                    }
                }
            }
            return instance!!
        }
    }
}