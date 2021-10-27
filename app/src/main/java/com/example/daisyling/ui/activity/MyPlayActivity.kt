package com.example.daisyling.ui.activity

import android.os.Message
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daisyling.common.base.BaseActivity
import com.example.daisyling.databinding.ActivityMyPlayBinding
import com.example.daisyling.db.AppDatabase
import com.example.daisyling.db.TrackDao
import com.example.daisyling.ui.adapter.CommonRvAdapter
import kotlin.concurrent.thread

/**
 * Created by Emily on 10/19/21
 */
class MyPlayActivity : BaseActivity<ActivityMyPlayBinding>() {
    private lateinit var trackDao: TrackDao
    private lateinit var adapter: CommonRvAdapter

    override fun getViewBinding() = ActivityMyPlayBinding.inflate(layoutInflater)

    override fun initView() {
        //Query data
        trackDao = AppDatabase.getDatabase(this).trackDao()
        thread {
            val trackList = trackDao.loadAllTracks()
            if (trackList.isNotEmpty()) {
                binding.tvDelete.visibility=View.VISIBLE
                binding.rv.visibility = View.VISIBLE
                binding.rv.layoutManager = LinearLayoutManager(this)
                adapter = CommonRvAdapter( this,trackList)
                binding.rv.adapter = adapter
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
            //Delete data
            trackDao = AppDatabase.getDatabase(this).trackDao()
            thread {
                trackDao.deleteAllTracks(trackDao.loadAllTracks())
            }
            binding.tvDelete.visibility = View.GONE
            binding.rv.visibility = View.GONE
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