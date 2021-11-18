package com.example.daisyling.ui.fragment

import android.content.Intent
import android.os.Message
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.daisyling.R
import com.example.daisyling.common.base.BaseFragment
import com.example.daisyling.common.util.CacheUtil
import com.example.daisyling.common.util.Utils.showToast
import com.example.daisyling.databinding.FragmentMyBinding
import com.example.daisyling.ui.activity.*

/**
 * Created by Emily on 9/30/21
 */
class MyFragment : BaseFragment<FragmentMyBinding>() {
    override fun getBinding(inflater: LayoutInflater, viewGroup: ViewGroup?) =
        FragmentMyBinding.inflate(inflater, viewGroup, false)

    override fun onResume() {
        super.onResume()
        val cache = CacheUtil.util.getCacheSize(_mActivity)
        if (!TextUtils.isEmpty(cache)) {
            binding.tvCache.text = cache
        } else {
            binding.tvCache.text = getString(R.string.default_cache)
        }
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initListener() {
        binding.tvMyVip.setOnClickListener {
            showToast(_mActivity, getString(R.string.develop_tip))
        }

        binding.tvMyDownload.setOnClickListener {
            val intent = Intent(_mActivity, MyDownLoadActivity::class.java)
            startActivity(intent)
        }

        binding.tvMyFavorite.setOnClickListener {
            val intent = Intent(_mActivity, MyFavoriteActivity::class.java)
            startActivity(intent)
        }

        binding.tvMyPlay.setOnClickListener {
            val intent = Intent(_mActivity, MyPlayHistoryActivity::class.java)
            startActivity(intent)
        }

        binding.rlMyCache.setOnClickListener {
            showCacheDialog()
        }

        binding.rlMyAppVersion.setOnClickListener {
            val intent = Intent(_mActivity, MyAppVersionActivity::class.java)
            startActivity(intent)
        }

        binding.rlMyLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showCacheDialog() {
        val builder = AlertDialog.Builder(_mActivity)
        builder.setMessage(getString(R.string.clear_cache_content))
        builder.setPositiveButton(
            getString(R.string.ok)
        ) { _, _ ->
            binding.tvCache.text = getString(R.string.default_cache)
            CacheUtil.util.clearCache(_mActivity)
            showToast(_mActivity, getString(R.string.clear_cache_success))
        }
        builder.setNegativeButton(
            getString(R.string.cancel)
        ) { _, _ -> }
        val alert = builder.create()
        alert.show()
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(_mActivity)
        builder.setMessage(getString(R.string.logout_content))
        builder.setPositiveButton(
            getString(R.string.ok)
        ) { _, _ ->
            logout()
        }
        builder.setNegativeButton(
            getString(R.string.cancel)
        ) { _, _ -> }
        val alert = builder.create()
        alert.show()
    }

    private fun logout() {
        val intent = Intent(_mActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        _mActivity.finish()
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