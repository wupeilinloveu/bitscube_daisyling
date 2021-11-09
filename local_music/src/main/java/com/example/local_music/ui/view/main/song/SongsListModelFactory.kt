package com.example.local_music.ui.view.main.song

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.local_music.data.SongRepository
import com.example.local_music.data.SongsRepository

/**
 * Created by Emily on 11/1/21
 */
class SongsListModelFactory(
    private val repository: SongsRepository,
    private val songRepository: SongRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SongsListViewModel(repository, songRepository) as T
    }
}