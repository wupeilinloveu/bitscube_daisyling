package com.example.daisyling.ui.activity

import android.os.Message
import android.view.View
import android.widget.RadioGroup
import com.example.daisyling.R
import com.example.daisyling.common.base.BaseActivity
import com.example.daisyling.databinding.ActivityMainBinding
import com.example.daisyling.ui.fragment.HomeFragment
import com.example.daisyling.ui.fragment.MyFragment

/**
 * Created by Emily on 9/30/21
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var homeFragment: HomeFragment
    private lateinit var myFragment: MyFragment

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initListener() {
        homeFragment = HomeFragment()
        myFragment = MyFragment()

        loadMultipleRootFragment(
            R.id.container,
            0,
            homeFragment,
            myFragment
        )

        binding.radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, position ->
            when (radioGroup.checkedRadioButtonId) {
                R.id.rb_home -> {
                    showHideFragment(homeFragment)
                }
                R.id.rb_my -> {
                    showHideFragment(myFragment)
                }
            }
        })
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