package com.example.local_music.ui.view.main.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.local_music.data.SongRepository
import com.example.local_music.data.SongsRepository

/**
 * Created by Emily on 11/1/21
 */
class PlayModelFactory(
    private val repository: SongRepository,
    private val songsRepository: SongsRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlayViewModel(
            repository, songsRepository
        ) as T
    }
}