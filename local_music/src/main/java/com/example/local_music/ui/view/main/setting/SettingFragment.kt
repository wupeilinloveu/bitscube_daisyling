package com.example.local_music.ui.view.main.setting

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.local_music.R
import com.example.local_music.ui.adapter.SettingListAdapter
import com.example.local_music.ui.base.BaseFragment
import kotlinx.android.synthetic.main.lm_main_fragment_setting.*

/**
 * Created by Emily on 11/1/21
 */
class SettingFragment : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.lm_main_fragment_setting

    override fun initView() {
        val moreList =
            arrayOf(getString(R.string.lm_setting_find_song), getString(R.string.lm_setting_about))
        val itemAdapter = SettingListAdapter(moreList.toList())
        lm_main_setting_list.layoutManager = LinearLayoutManager(context)
        lm_main_setting_list.adapter = itemAdapter

        itemAdapter.clickListener = {
            when (it) {
                0 -> startActivity(Intent(context, FindSongActivity::class.java))
                1 -> startActivity(Intent(context, AboutActivity::class.java))
            }
        }
    }

    override fun initData() {
    }

    companion object {
        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
    }
}