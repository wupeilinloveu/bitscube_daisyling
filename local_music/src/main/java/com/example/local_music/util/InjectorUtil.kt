package com.example.local_music.util

import com.example.local_music.data.SongRepository
import com.example.local_music.data.SongsRepository
import com.example.local_music.data.db.MusicDatabase
import com.example.local_music.ui.view.main.play.PlayModelFactory
import com.example.local_music.ui.view.main.setting.FindSongModelFactory
import com.example.local_music.ui.view.main.song.SongListModelFactory
import com.example.local_music.ui.view.main.song.SongsListModelFactory
import com.example.local_music.ui.view.main.song.edit.SongEditModelFactory
import com.example.local_music.ui.view.main.song.edit.SongsEditModelFactory

/**
 * Created by Emily on 11/1/21
 */
object InjectorUtil {
    private fun getSongRepository() = SongRepository.getInstance(MusicDatabase.getSongDao())
    private fun getSongsRepository() = SongsRepository.getInstance(MusicDatabase.getSongsDao())

    fun getSongsModelFactory() = SongsListModelFactory(getSongsRepository(), getSongRepository())
    fun getSongModelFactory() = SongListModelFactory(getSongRepository(), getSongsRepository())
    fun getPlayModelFactory() = PlayModelFactory(getSongRepository(), getSongsRepository())
    fun getFindSongModelFactory() = FindSongModelFactory(getSongRepository())

    fun getSongEditModelFactory() = SongEditModelFactory(getSongRepository())
    fun getSongsEditModelFactory() = SongsEditModelFactory(getSongsRepository())

}