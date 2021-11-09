package com.example.local_music.ui.view.main.song.add

import android.content.Context
import com.example.local_music.R
import com.example.local_music.ui.base.BaseDialog
import kotlinx.android.synthetic.main.lm_song_dialog_add_songs.*

/**
 * Created by Emily on 11/1/21
 */
class AddSongsDialog(context: Context) : BaseDialog(context, R.style.lm_dialog_base) {
    override val layoutId = R.layout.lm_song_dialog_add_songs

    interface DialogResult {
        fun addResult(name: String);
    }

    lateinit var dialogResult: DialogResult

    override fun initView() {

        lm_common_dialog_candle_bt?.setOnClickListener { dismiss() }
        lm_common_dialog_measure_bt?.setOnClickListener {
            val name = lm_song_songs_add_name_et?.text.toString()
            if (name.isNotEmpty())
                dialogResult.addResult(name)
            else
                showMsg("输入为空")
        }
    }
}