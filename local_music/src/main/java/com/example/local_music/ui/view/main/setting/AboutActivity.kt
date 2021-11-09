package com.example.local_music.ui.view.main.setting

import com.example.local_music.R
import com.example.local_music.ui.base.BaseActivity

/**
 * Created by Emily on 11/1/21
 */
class AboutActivity : BaseActivity() {
    override val layoutId: Int = R.layout.lm_setting_activity_about

    override fun initView() {
        supportActionBar?.hide()
        setTitleName(getString(R.string.lm_setting_about))
    }

    override fun initData() {
    }
}