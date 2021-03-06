package com.example.daisyling.ui.activity

import android.os.Message
import android.view.View
import com.example.daisyling.common.base.BaseActivity
import com.example.daisyling.databinding.ActivityMyAppVersionBinding

/**
 * Created by Emily on 10/19/21
 */
class MyAppVersionActivity : BaseActivity<ActivityMyAppVersionBinding>() {
    override fun getViewBinding() = ActivityMyAppVersionBinding.inflate(layoutInflater)

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initListener() {
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    override fun onClick(v: View?) {
    }

    override fun onHttpSuccess(reqType: Int, msg: Message) {
    }

    override fun onHttpError(reqType: Int) {
    }

    override fun onHttpError(reqType: Int, error: String) {
    }

    override fun onHttpFailure(reqType: Int, error: String) {
    }

    override fun onServiceError(reqType: Int) {
    }
}