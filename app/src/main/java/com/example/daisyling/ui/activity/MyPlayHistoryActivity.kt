package com.example.daisyling.ui.activity

import android.os.Message
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daisyling.common.base.BaseActivity
import com.example.daisyling.databinding.ActivityMyPlayHistoryBinding
import com.example.daisyling.db.AppDatabase
import com.example.daisyling.db.MyPlayHistoryDao
import com.example.daisyling.ui.adapter.TrackDbRvAdapter
import kotlin.concurrent.thread

/**
 * Created by Emily on 10/19/21
 */
class MyPlayHistoryActivity : BaseActivity<ActivityMyPlayHistoryBinding>() {
    private lateinit var myPlayHistoryDao: MyPlayHistoryDao
    private lateinit var adapter: TrackDbRvAdapter

    override fun getViewBinding() = ActivityMyPlayHistoryBinding.inflate(layoutInflater)

    override fun initView() {
        //Query data
        myPlayHistoryDao = AppDatabase.getDatabase(this).myPlayHistoryDao()
        thread {
            val trackList = myPlayHistoryDao.loadAllTracks()
            if (trackList.isNotEmpty()) {
                binding.tvDelete.visibility=View.VISIBLE
                binding.rv.visibility = View.VISIBLE
                binding.rv.layoutManager = LinearLayoutManager(this)
                adapter = TrackDbRvAdapter( this,trackList,"audio/mp4a-latm")
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
//            trackDao = TrackDatabase.getDatabase(this).trackDao()
//            thread {
//                trackDao.deleteAllTracks(trackDao.loadAllTracks())
//            }
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