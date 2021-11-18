package com.example.daisyling.ui.activity

import android.os.Message
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daisyling.common.base.BaseActivity
import com.example.daisyling.databinding.ActivityMyDownloadBinding
import com.example.daisyling.db.AppDatabase
import com.example.daisyling.db.MyDownLoadDao
import com.example.daisyling.ui.adapter.TrackDbRvAdapter
import kotlin.concurrent.thread

/**
 * Created by Emily on 10/19/21
 */
class MyDownLoadActivity : BaseActivity<ActivityMyDownloadBinding>() {
    private lateinit var myDownLoadDao: MyDownLoadDao
    private lateinit var adapter: TrackDbRvAdapter

    override fun getViewBinding() = ActivityMyDownloadBinding.inflate(layoutInflater)

    override fun initView() {
        //Query data
        myDownLoadDao = AppDatabase.getDatabase(this).myDownLoadDao()
        thread {
            val trackList = myDownLoadDao.loadAllTracks()
            if (trackList.isNotEmpty()) {
                binding.rv.visibility = View.VISIBLE
                binding.rv.layoutManager = LinearLayoutManager(this)
                adapter = TrackDbRvAdapter(this,trackList,"audio/mp4a-latm")
                binding.rv.adapter = adapter
            }else{
                binding.rv.visibility = View.GONE
            }
        }
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