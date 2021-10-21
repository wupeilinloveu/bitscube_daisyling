package com.example.daisyling.ui.activity

import android.os.Message
import android.view.View
import com.example.daisyling.common.base.BaseActivity
import com.example.daisyling.databinding.ActivityMyHistoryBinding
import com.example.daisyling.db.AnkoSQLiteManager
import com.example.daisyling.db.User
import com.example.daisyling.ui.adapter.CommonLvAdapter
import org.jetbrains.anko.doAsync

/**
 * Created by Emily on 10/19/21
 */
class MyHistoryActivity : BaseActivity<ActivityMyHistoryBinding>() {
    private lateinit var adapter: CommonLvAdapter
    private var data = ArrayList<User>()

    override fun getViewBinding() = ActivityMyHistoryBinding.inflate(layoutInflater)

    override fun initView() {
        doAsync {
            data = AnkoSQLiteManager().selectAllUsers()
            if (data.size > 0) {
                binding.tvDelete.visibility = View.VISIBLE
                binding.lv.visibility=View.VISIBLE
                adapter = CommonLvAdapter(this@MyHistoryActivity, data)
                binding.lv.adapter = adapter
            }
        }
    }

    override fun initData() {
    }

    override fun initListener() {
        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.tvDelete.setOnClickListener {
            AnkoSQLiteManager().deleteUser()
            binding.tvDelete.visibility = View.GONE
            binding.lv.visibility = View.GONE
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