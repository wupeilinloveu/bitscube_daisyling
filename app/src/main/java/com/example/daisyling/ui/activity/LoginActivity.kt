package com.example.daisyling.ui.activity

import android.content.Intent
import android.os.Message
import android.text.TextUtils
import android.view.View
import com.example.daisyling.R
import com.example.daisyling.common.base.BaseActivity
import com.example.daisyling.common.util.Utils.showToast
import com.example.daisyling.databinding.ActivityLoginBinding

/**
 * Created by Emily on 9/30/21
 */
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initListener() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim { it <= ' ' }
            val password = binding.etPassword.text.toString().trim { it <= ' ' }

            if (TextUtils.isEmpty(username)) {
                showToast(this, getString(R.string.login_empty_username))
            } else if (TextUtils.isEmpty(password)) {
                showToast(this, getString(R.string.login_empty_password))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
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