package com.example.local_music.data.db

/**
 * Created by Emily on 11/1/21
 */
object MusicDatabase {
    private var songDao: SongDao? = null

    private var songsDao: SongsDao? = null

    private var playSongDao: PlaySongDao? = null

    fun getSongsDao(): SongsDao {
        if (songsDao == null) {
            songsDao = SongsDao()
        }
        return songsDao as SongsDao
    }

    fun getSongDao(): SongDao {
        if (songDao == null) {
            songDao = SongDao()
        }
        return songDao!!
    }

    fun getPlaySongDao(): PlaySongDao {
        if (playSongDao == null) {
            playSongDao = PlaySongDao()
        }
        return playSongDao!!
    }
}