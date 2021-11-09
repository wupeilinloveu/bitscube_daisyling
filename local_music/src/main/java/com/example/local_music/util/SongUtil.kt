package com.example.local_music.util

import android.media.MediaMetadataRetriever
import com.example.local_music.data.model.play.Song
import com.example.local_music.player.PlayManager
import java.io.File

/**
 * Created by Emily on 11/1/21
 */
object SongUtil {
    fun getSong(songsId: Long, mmr: MediaMetadataRetriever, file: File): Song {
        val song = Song(songsId, file.name, file.nameWithoutExtension, file.path)
        try {
            mmr.setDataSource(file.path)
            val title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
            title?.let { if (it.isNotBlank()) song.title = title }
            val album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
            album?.let { if (it.isNotBlank()) song.album = album }
            val artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
            artist?.let { if (it.isNotBlank()) song.artist = artist }
            val duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            song.duration = duration?.toInt() ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return song
    }

    fun getImage(mmr: MediaMetadataRetriever, path: String): ByteArray? {
        try {
            mmr.setDataSource(path)
            return mmr.embeddedPicture
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun checkIsPlay(song: Song?): Boolean? {
        val playSong = PlayManager.getInstance().getPlaySong()
        playSong?.let {
            Logger.d(PlayManager.getInstance().getPlaying().toString())
            return song?.id == playSong.songId && PlayManager.getInstance().getPlaying()
        }
        return false
    }
}