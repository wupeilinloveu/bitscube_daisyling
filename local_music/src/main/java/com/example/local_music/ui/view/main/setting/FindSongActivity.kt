package com.example.local_music.ui.view.main.setting

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.example.local_music.databinding.LmSettingActivityFindSongBinding
import com.example.local_music.R
import com.example.local_music.ui.base.BaseActivity
import com.example.local_music.util.InjectorUtil

/**
 * Created by Emily on 11/1/21
 */
class FindSongActivity : BaseActivity() {
    override val layoutId: Int = R.layout.lm_setting_activity_find_song
    override val openBind = true

    private val binding by lazy {
        DataBindingUtil.setContentView<LmSettingActivityFindSongBinding>(
            this,
            layoutId
        )
    }
    private val viewModel by lazy {
        ViewModelProvider(ViewModelStore(), InjectorUtil.getFindSongModelFactory()).get(
            FindSongViewModel::class.java
        )
    }

    override fun initView() {
        supportActionBar?.hide()
        binding.viewModel = viewModel
        setTitleName(getString(R.string.lm_setting_find_song))
    }

    override fun initData() {
        viewModel.findFile()
    }
}