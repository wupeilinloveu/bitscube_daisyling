package com.example.local_music.ui.view.main

import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.viewpager.widget.ViewPager
import com.example.local_music.R
import com.example.local_music.ui.adapter.SectionsPagerAdapter
import com.example.local_music.ui.base.BaseActivity
import com.example.local_music.ui.view.main.setting.SettingFragment
import com.example.local_music.ui.view.main.song.SongsListFragment
import kotlinx.android.synthetic.main.lm_main_activity_main.*
import kotlinx.android.synthetic.main.lm_main_layout_toolbar.*

/**
 * Created by Emily on 11/1/21
 */
class CommonMusicActivity : BaseActivity(), CompoundButton.OnCheckedChangeListener {
    private val defaultPageIndex = 0

    private lateinit var mTitles: Array<String>
    private var radioButtons: MutableList<RadioButton> = arrayListOf()

    override val layoutId: Int
        get() = R.layout.lm_main_activity_main

    override fun initView() {
        initToolBar()
        initViewPager()
    }

    override fun initData() {
    }

    private fun initToolBar() {
        setSupportActionBar(lm_main_toolbar)

        mTitles = resources.getStringArray(R.array.lm_main_titles)
        lm_main_tb_play_list_rg.setOnCheckedChangeListener(this)
        lm_main_tb_music_rg.setOnCheckedChangeListener(this)
        lm_main_tb_settings_rg.setOnCheckedChangeListener(this)
        radioButtons.add(lm_main_tb_music_rg)
        radioButtons.add(lm_main_tb_play_list_rg)
        radioButtons.add(lm_main_tb_settings_rg)

        radioButtons[defaultPageIndex].isChecked = (true)
    }

    private fun initViewPager() {
        val adapter = SectionsPagerAdapter(
            this, supportFragmentManager,
            arrayOf(
                MusicFragment.newInstance(),
                SongsListFragment.newInstance(),
                SettingFragment.newInstance()
            )
        )

        lm_main_view_pager.adapter = (adapter)

        lm_main_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                radioButtons[position].isChecked = (true)
            }
        })
    }

    override fun onCheckedChanged(button: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            onItemChecked(radioButtons.indexOf(button))
        }
    }

    private fun onItemChecked(position: Int) {
        lm_main_view_pager.currentItem = (position)
        lm_main_toolbar.title = (mTitles[position])
    }

    override fun onBackPressed() {
        finish()
//        CommonDialog
//            .Builder(this)
//            .setMessage(getString(R.string.lm_main_exit_app_remind))
//            .setPositiveButton(null, View.OnClickListener {
//                finish()
//                PlayManager.getInstance().exit()
//            })
//            .setNegativeButton(null, null)
//            .show()
    }
}