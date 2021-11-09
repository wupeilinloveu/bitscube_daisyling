package com.example.daisyling.ui.activity

import android.os.Message
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daisyling.common.base.BaseActivity
import com.example.daisyling.databinding.ActivityMyDownloadBinding
import com.example.daisyling.db.TrackDao
import com.example.daisyling.db.TrackDatabase
import com.example.daisyling.ui.adapter.CommonRvAdapter
import kotlin.concurrent.thread

/**
 * Created by Emily on 10/19/21
 */
class MyDownLoadActivity : BaseActivity<ActivityMyDownloadBinding>() {
    private lateinit var trackDao: TrackDao
    private lateinit var adapter: CommonRvAdapter

    override fun getViewBinding() = ActivityMyDownloadBinding.inflate(layoutInflater)

    override fun initView() {
        //Query data
        trackDao = TrackDatabase.getDatabase(this).trackDao()
        thread {
            val trackList = trackDao.loadAllTracks()
            if (trackList.isNotEmpty()) {
                binding.rv.visibility = View.VISIBLE
                binding.rv.layoutManager = LinearLayoutManager(this)
                adapter = CommonRvAdapter(this,trackList)
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