package com.example.local_music.player

import android.media.MediaPlayer
import com.example.local_music.data.model.play.PlaySong
import com.example.local_music.util.Logger
import java.io.IOException

/**
 * Created by Emily on 11/1/21
 */
class Player {
    var playSong: PlaySong? = null

    private val mPlayer: MediaPlayer = MediaPlayer()

    var playCallback: PlayCallBack? = null

    interface PlayCallBack {
        fun onCompletion(song: PlaySong?)
        fun onPause(song: PlaySong?)
        fun onPlay(song: PlaySong?)
    }

    init {
        mPlayer.setOnCompletionListener { finish() }
    }

    fun init(playSong: PlaySong) {
        this.playSong = playSong
    }

    fun play(): Boolean {
        if (playSong == null) return false
        try {
            mPlayer.reset()
            mPlayer.setDataSource(playSong?.path)
            mPlayer.prepare()
            mPlayer.start()
            playCallback?.onPlay(playSong)
            return true
        } catch (e: IOException) {
            Logger.e(e)
        }
        return false
    }

    fun continuePlay() {
        if (!mPlayer.isPlaying && playSong != null) {
            mPlayer.start()
            playCallback?.onPlay(playSong)
        }
    }

    fun play(song: PlaySong?): Boolean {
        this.playSong = song
        return play()
    }

    fun pause() {
        if (mPlayer.isPlaying) {
            mPlayer.pause()
            playCallback?.onPause(playSong)
        }
    }

    fun seekTo(progress: Int): Boolean {
        if (playSong == null) return false
        if (mPlayer.duration <= progress) {
            finish()
        } else {
            mPlayer.seekTo(progress)
        }
        return true
    }

    val playing: Boolean get() = mPlayer.isPlaying

    val progress: Int get() = mPlayer.currentPosition

    val duration: Int get() = mPlayer.duration

    private fun finish() {
        playSong == null
        playCallback?.onCompletion(playSong)
    }
}