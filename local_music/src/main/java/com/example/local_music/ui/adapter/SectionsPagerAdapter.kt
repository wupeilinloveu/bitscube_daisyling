package com.example.local_music.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by Emily on 11/1/21
 */
class SectionsPagerAdapter @JvmOverloads constructor(
    private val mContext: Context,
    fm: FragmentManager?,
    private val fragments: Array<Fragment>,
    private val titles: Array<String>? = null
) : FragmentPagerAdapter(
    fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles?.get(position) ?: ""
    }

    override fun getCount(): Int {
        return fragments.size
    }
}