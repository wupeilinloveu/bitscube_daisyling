package com.example.local_music.ui.view.main

import com.example.local_music.R
import com.example.local_music.ui.adapter.SectionsPagerAdapter
import com.example.local_music.ui.base.BaseFragment
import com.example.local_music.ui.view.main.play.LyricFragment
import com.example.local_music.ui.view.main.play.PlayFragment
import kotlinx.android.synthetic.main.lm_main_fragment_home.*

/**
 * Created by Emily on 11/1/21
 */
class MusicFragment : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.lm_main_fragment_home

    override fun initView() {
        lm_main_home_view_pager?.adapter = SectionsPagerAdapter(
            requireContext(), childFragmentManager,
            arrayOf(
                PlayFragment.newInstance(),
                LyricFragment.newInstance(0)
            )
        )
    }

    override fun initData() {
    }

    companion object {
        fun newInstance(): MusicFragment {
            return MusicFragment()
        }
    }
}