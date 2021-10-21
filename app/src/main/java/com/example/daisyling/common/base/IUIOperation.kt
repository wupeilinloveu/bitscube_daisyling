package com.example.daisyling.common.base

import android.view.View

/**
 * Created by Emily on 9/30/21
 */
interface IUIOperation : View.OnClickListener {
    fun initView()

    fun initData()

    fun initListener()
}

