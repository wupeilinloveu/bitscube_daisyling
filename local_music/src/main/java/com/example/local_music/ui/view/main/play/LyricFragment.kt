package com.example.local_music.ui.view.main.play

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.local_music.R
import com.example.local_music.ui.base.BaseFragment

/**
 * Created by Emily on 11/1/21
 */
class LyricFragment : BaseFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)
        var index = 1
        arguments?.let { index = it.getInt(ARG_SECTION_NUMBER) }

        pageViewModel?.setIndex(index)
    }

    override val layoutId: Int
        get() = R.layout.lm_play_fragment_lyric

    override fun initView() {
//        val textView = mView.findViewById<TextView>(R.id.section_label)
//        pageViewModel.text.observe(this, Observer { s ->
//            if (textView != null) {
//                textView.text = s
//            }
//        })
    }

    override fun initData() {
    }

    private var pageViewModel: PageViewModel? = null

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        fun newInstance(index: Int): LyricFragment {
            val fragment = LyricFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }
}