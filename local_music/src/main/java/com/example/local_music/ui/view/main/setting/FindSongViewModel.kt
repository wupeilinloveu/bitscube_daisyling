package com.example.local_music.ui.view.main.setting

import android.media.MediaMetadataRetriever
import android.os.Environment.getExternalStorageDirectory
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.local_music.R
import com.example.local_music.data.SongRepository
import com.example.local_music.data.constant.ConstantParam
import com.example.local_music.player.PlayManager
import com.example.local_music.ui.base.BaseViewModel
import com.example.local_music.util.SongUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.litepal.LitePalApplication
import java.io.File

/**
 * Created by Emily on 11/1/21
 */
class FindSongViewModel(
    private val songRepository: SongRepository
) : BaseViewModel() {

    /**
     * Current file path
     */
    var filePath: ObservableField<String> = ObservableField()

    /**
     * Search
     */
    var searchMsg: ObservableField<String> = ObservableField()

    private val points = arrayOf(".", "..", "...", "....", ".....") //Prompt suffix
    private var num = 0  // Song num
    private var fileNum = 0 //File num
    private val showMargin = 10

    override fun init() {}

    fun findFile() {
        val rootFile = getExternalStorageDirectory().absolutePath

        num = 0
        fileNum = 0

        searchMsg.set(LitePalApplication.getContext().getString(R.string.lm_setting_searching))

        viewModelScope.launch {
            delay(500)
            searchFile(rootFile)
            filePath.set("共导入${num}首歌曲")
            searchMsg.set(LitePalApplication.getContext().getString(R.string.lm_setting_search_end))
            PlayManager.getInstance().updatePlayList()
        }

    }

    /**
     * Search file
     */
    private suspend fun searchFile(path: String) {
        val file = File(path)
        val fileList = file.listFiles()
        for (f in fileList) {
            if (fileNum % showMargin == 0) {
                delay(1)
                searchMsg.set(LitePalApplication.getContext().getString(R.string.lm_setting_searching) + points[fileNum / showMargin % points.size])
            }
            fileNum++
            filePath.set(f.absolutePath)
            if (!f.canRead()) continue
            if (f.isDirectory) {
                //not scan hidden folders
                if (!f.name.startsWith(".")) {
                    searchFile(f.absolutePath)
                }
            } else {
                if ("mp3" == f.extension) {
                    addSong(f)
                    delay(100)
                }
            }
        }
    }

    private fun addSong(file: File) {
        val mmr = MediaMetadataRetriever()
        val song = SongUtil.getSong(ConstantParam.SONGS_ID_LOCAL, mmr, file)
        if (songRepository.getSong(ConstantParam.SONGS_ID_LOCAL, file.path) == null) {
            songRepository.addSong(song)
            num++
        }
    }
}