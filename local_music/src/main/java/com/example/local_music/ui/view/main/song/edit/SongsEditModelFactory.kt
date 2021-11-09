package com.example.local_music.ui.view.main.song.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.local_music.data.SongsRepository

/**
 * Created by Emily on 11/1/21
 */
class SongsEditModelFactory(
    private val repository: SongsRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SongsEditViewModel(repository) as T
    }
}