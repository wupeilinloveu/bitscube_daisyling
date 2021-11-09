package com.example.local_music.ui.view.main.play

import android.os.CountDownTimer
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.local_music.data.SongRepository
import com.example.local_music.data.SongsRepository
import com.example.local_music.data.constant.ConstantParam
import com.example.local_music.data.model.play.PlaySong
import com.example.local_music.data.model.play.Song
import com.example.local_music.data.model.play.Songs
import com.example.local_music.player.PlayListener
import com.example.local_music.player.PlayManager
import com.example.local_music.player.PlayType
import com.example.local_music.ui.base.BaseViewModel
import com.example.local_music.util.TimeUtils

/**
 * Created by Emily on 11/1/21
 */
class PlayViewModel(
    private val repository: SongRepository,
    private val songsRepository: SongsRepository
) : BaseViewModel(),
    PlayListener {
    var playSong: MutableLiveData<PlaySong> = MutableLiveData()
    var playState: MutableLiveData<Boolean> = MutableLiveData()
    var markState: MutableLiveData<Boolean> = MutableLiveData()

    var playProgress: ObservableField<Int> = ObservableField()
    var playProgressStr: ObservableField<String> = ObservableField()
    var playDuration: ObservableField<Int> = ObservableField()
    var playTitle: ObservableField<String> = ObservableField()
    var playDurationStr: ObservableField<String> = ObservableField()

    private var timer: CountDownTimer? = null

    fun getPlayType(): PlayType {
        return PlayManager.getInstance().playType
    }

    fun previous() {
        PlayManager.getInstance().previous()
    }

    fun next() {
        PlayManager.getInstance().next()
    }

    fun changePlayType(): PlayType {
        return PlayManager.getInstance().changePlayType()
    }

    fun changePlay() {
        if (playState.value == true) {
            PlayManager.getInstance().pause()
        } else {
            PlayManager.getInstance().play()
        }
    }

    fun setSongState() {
        playState.value = PlayManager.getInstance().getPlaying()
        markState.value = checkMark(PlayManager.getInstance().getPlaySong()?.castSong())
        val duration = PlayManager.getInstance().getDuration()
        val progress = PlayManager.getInstance().getProgress()
        playProgress.set(progress)
        playDuration.set(duration)
        playDurationStr.set(TimeUtils.formatDuration(duration))
        var title = (PlayManager.getInstance().getPlaySong()?.title)
        if (PlayManager.getInstance().getPlaySong()?.artist?.isNotEmpty() == true) {
            title = (title + " - " + PlayManager.getInstance().getPlaySong()?.artist)
        }
        playTitle.set(title)
    }

    fun seekTo(process: Int) {
        PlayManager.getInstance().pause()
        PlayManager.getInstance().seekTo(process)
        PlayManager.getInstance().play()
    }

    override fun onPlayerPlay() {
        playSong.value = PlayManager.getInstance().getPlaySong()
        playState.value = true
        setSongState()
        val duration = PlayManager.getInstance().getDuration()
        val progress = PlayManager.getInstance().getProgress()
        timer = object : CountDownTimer(duration - progress.toLong(), 1000) {
            override fun onFinish() {}

            override fun onTick(millisUntilFinished: Long) {
                val time = duration - millisUntilFinished.toInt()
                playProgress.set(time)
            }
        }.start()
    }

    override fun onPlayerPause() {
        playState.value = false
        timer?.cancel()
    }

    fun changeMark() {
        val song = PlayManager.getInstance().getPlaySong()?.castSong()
        changeMark(song)
    }

    private fun changeMark(song: Song?) {
        if (checkMark(song)) {
            unMark(song)
            markState.value = false
        } else {
            mark(song)
            markState.value = true
        }
    }

    private fun checkMark(): Boolean {
        val song = PlayManager.getInstance().getPlaySong()?.castSong()
        return checkMark(song)
    }

    private fun checkMark(song: Song?): Boolean {
        val markId = ConstantParam.SONGS_ID_MARK
        if (song == null) return false
        return repository.getSong(markId, song.target) != null
    }

    fun getSongsList(): MutableList<Songs> {
        return songsRepository.getLocalSongsList()
    }

    fun addSong(song: Song): Boolean {
        if (repository.getSong(song.songs_id, song.target) != null) {
            return false
        }
        repository.addSong(song)
        return true
    }

    fun getPlaySong(): PlaySong? {
        return PlayManager.getInstance().getPlaySong()
    }

    private fun mark(song: Song?) {
        val markId = ConstantParam.SONGS_ID_MARK
        if (song == null) return
        val tSong = song.clone(false)
        tSong.songs_id = markId
        repository.addSong(tSong)
    }

    private fun unMark(song: Song?) {
        val markId = ConstantParam.SONGS_ID_MARK
        if (song == null) return
        val tSong = repository.getSong(markId, song.target)
        if (tSong != null) {
            repository.deleteSong(tSong)
        }
    }

    override fun init() {
    }

    init {
        PlayManager.getInstance().playListener = this
    }
}