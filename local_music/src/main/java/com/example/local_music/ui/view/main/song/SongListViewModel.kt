package com.example.local_music.ui.view.main.song

import android.media.MediaMetadataRetriever
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.local_music.data.SongRepository
import com.example.local_music.data.SongsRepository
import com.example.local_music.data.model.play.Song
import com.example.local_music.data.model.play.Songs
import com.example.local_music.player.PlayManager
import com.example.local_music.ui.base.BaseViewModel
import com.example.local_music.util.SongUtil
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

/**
 * Created by Emily on 11/1/21
 */
class SongListViewModel(
    private val repository: SongRepository,
    private val songsRepository: SongsRepository
) : BaseViewModel() {
    var songsId: Long = 0
    var list: MutableList<Song> = mutableListOf()
    var dataChanged = MutableLiveData<Int>()

    fun getSongList() {

        launch {
            list.clear()
            list.addAll(repository.getSongList(songsId))
        }
    }

    fun addSong(song: Song): Boolean {
        if (repository.getSong(song.songs_id, song.target) != null) {
            return false
        }
        repository.addSong(song)
        list.add(song)
        return true
    }

    fun getTitle(): String? {
        var songs = songsRepository.getSongs(songsId)
        return songs?.name
    }

    fun getSongsList(): MutableList<Songs> {
        return songsRepository.getLocalSongsList()
    }

    fun getSong(id: Long): Song? {
        return repository.getSong(id)
    }

    fun delete(id: Long): Boolean {
        val ret = repository.delete(id)
        if (ret) {
            list.remove(list.find { song -> song.id == id })
            dataChanged.value = dataChanged.value?.plus(1)
        }
        return ret
    }

    fun addFile(paths: ArrayList<String?>?) {
        if (paths != null) {
            val mmr = MediaMetadataRetriever()
            for (path in paths) {
                if (path != null) {
                    val file = File(path)
                    val song = SongUtil.getSong(songsId, mmr, file)
                    addSong(song)
                }
            }
            dataChanged.value = dataChanged.value?.plus(1)
            PlayManager.getInstance().updatePlayList()
        }
    }

    fun play(pos: Int) {
        val id = list[pos].id
        if (id == PlayManager.getInstance().getPlaySong()?.songId) {
            if (PlayManager.getInstance().getPlaying()) {
                PlayManager.getInstance().pause()
            } else {
                PlayManager.getInstance().play()
            }
        } else {
            val song = getSong(id)
            song?.let {
                PlayManager.getInstance().addSong(
                    song.castSongPlay(),
                    PlayManager.getInstance().getCurrentIndex()
                )
            }
            PlayManager.getInstance().goto(PlayManager.getInstance().getCurrentIndex())
        }
    }

    fun prePlay(songId: Long) {
        val song = getSong(songId)
        song?.let {
            PlayManager.getInstance().addSong(
                song.castSongPlay(),
                PlayManager.getInstance().getCurrentIndex() + 1
            )
        }
    }

    private fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            block()
            dataChanged.value = dataChanged.value?.plus(1)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun init() {
    }
}