package com.example.local_music.ui.view.main.play

import android.media.MediaMetadataRetriever
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.example.local_music.databinding.LmPlayFragmentPlayBinding
import com.example.local_music.R
import com.example.local_music.data.constant.ShowMsg
import com.example.local_music.data.model.play.Songs
import com.example.local_music.player.PlayType
import com.example.local_music.ui.base.BaseFragment
import com.example.local_music.ui.view.main.song.add.AddToSongsPopup
import com.example.local_music.util.InjectorUtil
import com.example.local_music.util.SongUtil
import com.example.local_music.util.TimeUtils
import kotlinx.android.synthetic.main.lm_play_fragment_play.*

/**
 * Created by Emily on 11/1/21
 */
class PlayFragment : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.lm_play_fragment_play

    private val viewModel by lazy {
        ViewModelProvider(
            ViewModelStore(),
            InjectorUtil.getPlayModelFactory()
        ).get(PlayViewModel::class.java)
    }

    override fun initView() {
        val binding = view?.let { DataBindingUtil.bind<LmPlayFragmentPlayBinding>(it) }
        binding?.viewModel = viewModel

        setPlayModule(viewModel.getPlayType())
        val pop = activity?.let { PlayListPopup(it) }
        lm_play_control_list_bt.setOnClickListener { pop?.show() }
        lm_play_gramophone_view.setOnClickListener { viewModel.changePlay() }
        lm_play_control_start_bt.setOnClickListener {
            viewModel.changePlay()
        }
        lm_play_control_pre_bt.setOnClickListener { viewModel.previous() }
        lm_play_control_next_bt.setOnClickListener { viewModel.next() }
        lm_play_control_mode_bt.setOnClickListener { setPlayModule(viewModel.changePlayType()) }
        lm_play_control_mark_bt.setOnClickListener { viewModel.changeMark() }
        lm_play_control_add_bt.setOnClickListener { add() }

        lm_play_progress_seek_bar.setOnSeekBarChangeListener(object :
            OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                viewModel.playProgressStr.set(TimeUtils.formatDuration(progress))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                viewModel.seekTo(seekBar.progress)
            }
        })

        viewModel.markState.observe(this, androidx.lifecycle.Observer { setMark(it) })
        viewModel.playState.observe(this, androidx.lifecycle.Observer { changePlayState(it) })
        val mmr = MediaMetadataRetriever()
        viewModel.playSong.observe(this, androidx.lifecycle.Observer {
            SongUtil.getImage(mmr, it.path)
                ?.let { it1 -> lm_play_gramophone_view.setBitmapByte(it1) }
                ?: let { lm_play_gramophone_view.setPictureRes(R.mipmap.lm_play_default_picture) }
        })
    }

    /**
     * Set mark
     */
    private fun setMark(mark: Boolean) {
        if (mark) {
            lm_play_control_mark_bt.setImageResource(R.drawable.lm_play_ic_favorite_yes)
        } else {
            lm_play_control_mark_bt.setImageResource(R.drawable.lm_play_ic_favorite_no)
        }
    }

    /**
     * Change play state
     */
    private fun changePlayState(play: Boolean) {
        lm_play_gramophone_view.playing = play
        if (play) {
            lm_play_control_start_bt.setImageResource(R.drawable.lm_play_ic_pause)
        } else {
            lm_play_control_start_bt.setImageResource(R.drawable.lm_play_ic_play)
        }
    }

    /**
     * Set play module
     */
    private fun setPlayModule(playType: PlayType) {
        when (playType) {
            PlayType.LIST -> lm_play_control_mode_bt.setImageResource(R.drawable.lm_play_ic_play_mode_list)
            PlayType.LIST_LOOP -> lm_play_control_mode_bt.setImageResource(R.drawable.lm_play_ic_play_mode_loop)
//            PlayType.RANDOM -> lm_play_control_mode_bt.setImageResource(R.drawable.lm_play_ic_play_mode_random)
            PlayType.SINGLE_LOOP -> lm_play_control_mode_bt.setImageResource(R.drawable.lm_play_ic_play_mode_single)
        }
    }

    private fun add() {
        val addPop = activity?.let { AddToSongsPopup(viewModel.getSongsList(), it) }
        addPop?.callback = object : AddToSongsPopup.PopCallBack {
            override fun choice(pos: Int, songs: Songs) {
                val song = viewModel.getPlaySong()?.castSong()
                if (song != null) {
                    song.songs_id = songs.id
                    if (viewModel.addSong(song)) {
                        showMsg(ShowMsg.msg_add_ok)
                    } else {
                        showMsg(ShowMsg.msg_song_exist)
                    }

                }
            }
        }
        addPop?.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.setSongState()
    }

    override fun initData() {
    }

    companion object {
        fun newInstance(): PlayFragment {
            return PlayFragment()
        }
    }
}