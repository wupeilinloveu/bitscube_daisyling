package com.example.local_music.ui.view.main.song.edit

import com.example.local_music.data.SongsRepository
import com.example.local_music.data.model.play.Songs
import com.example.local_music.ui.base.BaseViewModel
import com.example.local_music.util.Logger

/**
 * Created by Emily on 11/1/21
 */
class SongsEditViewModel(private val repository: SongsRepository) : BaseViewModel() {
    var songsId = 0L

    var songs: Songs? = null

    override fun init() {
        songs = getSongs(songsId)
        Logger.d("init:${songsId}")
    }

    fun getTitle(): String {
        return songs?.name ?: ""
    }

    fun save() {
        songs?.let { repository.edit(it) }
    }

    fun getSongs(songsId: Long): Songs? {
        return repository.getSongs(songsId)
    }
}