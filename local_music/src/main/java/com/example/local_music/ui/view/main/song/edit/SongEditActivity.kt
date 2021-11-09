package com.example.local_music.ui.view.main.song.edit

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.example.local_music.databinding.LmSongActivitySongEditBinding
import com.example.local_music.R
import com.example.local_music.data.base.BaseBean
import com.example.local_music.data.constant.MessageType
import com.example.local_music.ui.base.BaseActivity
import com.example.local_music.util.InjectorUtil
import com.example.local_music.util.RxBus

/**
 * Created by Emily on 11/1/21
 */
class SongEditActivity : BaseActivity() {
    override val layoutId: Int = R.layout.lm_song_activity_song_edit
    override val openBind = true

    private val binding by lazy {
        DataBindingUtil.setContentView<LmSongActivitySongEditBinding>(
            this,
            layoutId
        )
    }

    private val viewModel by lazy {
        ViewModelProvider(
            ViewModelStore(),
            InjectorUtil.getSongEditModelFactory()
        ).get(SongEditViewModel::class.java)
    }

    override fun initView() {
        supportActionBar?.hide()
        binding?.viewModel = viewModel
    }

    override fun initData() {
        viewModel.songId = intent.getLongExtra("songId", 0)
        viewModel.init()
        setTitleName(viewModel.getTitle())
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.save()
        RxBus.send(BaseBean(MessageType.UPDATE_SONG))
    }

    companion object {
        fun actionStart(context: Context, songId: Long) {
            val intent = Intent(context, SongEditActivity::class.java)
            intent.putExtra("songId", songId)
            context.startActivity(intent)
        }
    }
}