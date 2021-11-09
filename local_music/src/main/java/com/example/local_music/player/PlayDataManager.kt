package com.example.local_music.player

import com.example.local_music.data.constant.ConstantParam
import com.example.local_music.data.db.MusicDatabase
import com.example.local_music.data.model.play.PlaySong
import com.example.local_music.util.Logger
import com.example.local_music.util.SharePrefUtil

/**
 * Created by Emily on 11/1/21
 */
object PlayDataManager {
    private const val keyName = "playManage"

    /**
     * Target index
     */
    fun getIndex(target: String, playList: MutableList<PlaySong>): Int {
        return playList.indexOf(playList.find { it.target == target })
    }

    /**
     * Read history
     */
    fun read(): PlayData {
        var info = PlayData()
        info.duration = readDuration()
        info.progress = readProgress()
        info.target = readTarget()
        info.playType = readPlayType()
        info.playList = readPlayList()
        return info
    }

    /**
     * Save history
     */
    fun write(playData: PlayData) {
        writeDuration(playData.duration)
        writeProgress(playData.progress)
        writeTarget(playData.target)
        writePlayType(playData.playType)
        writePlayList(playData.playList)
    }

    private fun readDuration(): Int {
        return SharePrefUtil.getIntData(keyName, "duration")
    }

    private fun writeDuration(duration: Int) {
        return SharePrefUtil.saveData(keyName, "duration", duration)
    }


    private fun readProgress(): Int {
        return SharePrefUtil.getIntData(keyName, "progress")
    }

    private fun writeProgress(progress: Int) {
        return SharePrefUtil.saveData(keyName, "progress", progress)
    }


    private fun readTarget(): String {
        return SharePrefUtil.getData(keyName, "target")
    }

    fun writeTarget(index: String) {
        return SharePrefUtil.saveData(keyName, "target", index)
    }

    private fun readPlayType(): PlayType {
        try {
            return PlayType.valueOf(SharePrefUtil.getData(keyName, "playType"))
        } catch (e: Exception) {
            Logger.e(e)
        }
        return PlayType.LIST
    }

    fun writePlayType(playType: PlayType) {
        return SharePrefUtil.saveData(keyName, "playType", playType.name)
    }

    private fun readPlayList(): MutableList<PlaySong> {
        var list = MusicDatabase.getPlaySongDao().getSongList()
        if (list.isEmpty()) {
            list = PlaySong.cast(
                MusicDatabase.getSongDao().getSongList(ConstantParam.SONGS_ID_LOCAL)
            ) as MutableList<PlaySong>
        }
        return list
    }

    fun writePlayList(playList: MutableList<PlaySong>) {
        MusicDatabase.getPlaySongDao().saveSongList(playList)
    }
}