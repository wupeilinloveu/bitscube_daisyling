package com.example.local_music.ui.view.main.song.edit

import com.example.local_music.data.SongRepository
import com.example.local_music.data.model.play.Song
import com.example.local_music.ui.base.BaseViewModel
import com.example.local_music.util.Logger

/**
 * Created by Emily on 11/1/21
 */
class SongEditViewModel(private val repository: SongRepository) : BaseViewModel() {
    var songId = 0L

    var song: Song? = null

    override fun init() {
        song = getSong(songId)
        Logger.d("init:${songId}")
    }

    fun getTitle(): String {
        return song?.title ?: ""
    }

    fun save() {
        song?.let { repository.edit(it) }
    }

    fun getSong(songId: Long): Song? {
        return repository.getSong(songId)
    }
}