package com.example.local_music.ui.view.main.play

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 * Created by Emily on 11/1/21
 */
class PageViewModel : ViewModel() {
    private val mIndex =
        MutableLiveData<Int>()
    val text =
        Transformations.map<Int, String>(
            mIndex
        ) { input -> "Input : $input" }

    fun setIndex(index: Int) {
        mIndex.value = index
    }

}