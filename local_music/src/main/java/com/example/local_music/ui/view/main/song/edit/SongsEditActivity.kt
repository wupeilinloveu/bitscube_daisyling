package com.example.local_music.ui.view.main.song.edit

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.example.local_music.R
import com.example.local_music.data.base.BaseBean
import com.example.local_music.data.constant.MessageType
import com.example.local_music.databinding.LmSongActivitySongsEditBinding
import com.example.local_music.ui.base.BaseActivity
import com.example.local_music.util.InjectorUtil
import com.example.local_music.util.RxBus

/**
 * Created by Emily on 11/1/21
 */
class SongsEditActivity : BaseActivity() {
    override val layoutId: Int = R.layout.lm_song_activity_songs_edit
    override val openBind = true

    var songsId = 0L

    private val binding by lazy {
        DataBindingUtil.setContentView<LmSongActivitySongsEditBinding>(
            this,
            layoutId
        )
    }

    private val viewModel by lazy {
        ViewModelProvider(
            ViewModelStore(),
            InjectorUtil.getSongsEditModelFactory()
        ).get(SongsEditViewModel::class.java)
    }

    override fun initView() {
        supportActionBar?.hide()
        binding?.viewModel = viewModel

    }

    override fun initData() {
        viewModel.songsId = intent.getLongExtra("songsId", 0)
        viewModel.init()
        setTitleName(viewModel.getTitle())
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.save()
        RxBus.send(BaseBean(MessageType.UPDATE_SONGS))
    }

    companion object {
        fun actionStart(context: Context, songsId: Long) {
            val intent = Intent(context, SongsEditActivity::class.java)
            intent.putExtra("songsId", songsId)
            context.startActivity(intent)
        }
    }
}