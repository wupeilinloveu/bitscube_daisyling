package com.example.daisyling.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daisyling.R
import com.example.daisyling.common.base.BaseFragment
import com.example.daisyling.common.util.Utils.showToast
import com.example.daisyling.databinding.FragmentMyBinding
import com.example.daisyling.db.AnkoSQLiteManager
import com.example.daisyling.db.User
import com.example.daisyling.ui.activity.*

/**
 * Created by Emily on 9/30/21
 */
class MyFragment : BaseFragment<FragmentMyBinding>() {
    private var data = ArrayList<User>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initListener() {
        binding.tvMyVip.setOnClickListener {
            showToast(_mActivity, getString(R.string.my_vip_toast))
        }

        binding.tvMyDownload.setOnClickListener {
            val intent = Intent(_mActivity, MyDownLoadActivity::class.java)
            startActivity(intent)
        }

        binding.tvMyFavorite.setOnClickListener {
            val intent = Intent(_mActivity, MyFavoriteActivity::class.java)
            startActivity(intent)
        }


        binding.tvMyHistory.setOnClickListener {
            val intent = Intent(_mActivity, MyHistoryActivity::class.java)
            startActivity(intent)
        }

        binding.tvMyPlay.setOnClickListener {
            val intent = Intent(_mActivity, MyPlayActivity::class.java)
            startActivity(intent)
        }

        binding.rlMyCache.setOnClickListener {
            data = AnkoSQLiteManager().selectAllUsers()
            if (data.size > 0) {
                AnkoSQLiteManager().deleteUser()
                showToast(_mActivity, getString(R.string.my_cache_history_clear))
            } else {
                showToast(_mActivity, getString(R.string.my_cache_no_history))
            }

        }

        binding.rlMyAppVersion.setOnClickListener {
            val intent = Intent(_mActivity, MyAppVersionActivity::class.java)
            startActivity(intent)
        }

        binding.rlMyLogout.setOnClickListener {
            val intent = Intent(_mActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            _mActivity.finish()
        }
    }

    override fun onClick(v: View?) {
    }

    override fun getBinding(inflater: LayoutInflater, viewGroup: ViewGroup?) =
        FragmentMyBinding.inflate(inflater, viewGroup, false)

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