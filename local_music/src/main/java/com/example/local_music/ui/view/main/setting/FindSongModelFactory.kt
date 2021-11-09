package com.example.local_music.ui.view.main.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.local_music.data.SongRepository

/**
 * Created by Emily on 11/1/21
 */
class FindSongModelFactory(
    private val songRepository: SongRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FindSongViewModel(songRepository) as T
    }
}