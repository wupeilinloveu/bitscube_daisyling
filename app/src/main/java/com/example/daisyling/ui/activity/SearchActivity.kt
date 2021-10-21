package com.example.daisyling.ui.activity

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daisyling.common.base.BaseActivity
import com.example.daisyling.common.base.Const
import com.example.daisyling.common.util.GsonUtil
import com.example.daisyling.common.util.SQLiteDatabase.deleteData
import com.example.daisyling.common.util.SQLiteDatabase.hasData
import com.example.daisyling.common.util.SQLiteDatabase.insertData
import com.example.daisyling.common.util.SQLiteDatabase.queryData
import com.example.daisyling.databinding.ActivitySearchBinding
import com.example.daisyling.model.bean.Music
import com.example.daisyling.model.bean.Video
import com.example.daisyling.model.protocol.IHttpService
import com.example.daisyling.presenter.CommonPresenter
import com.example.daisyling.ui.adapter.MusicRvQuickAdapter
import com.example.daisyling.ui.adapter.VideoRvQuickAdapter
import scut.carson_ho.searchview.ICallBack
import scut.carson_ho.searchview.RecordSQLiteOpenHelper

/**
 * Created by Emily on 10/11/21
 */
class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    private val mCallBack: ICallBack? = null
    private var helper: RecordSQLiteOpenHelper? = null
    private var commonPresenter: CommonPresenter? = null
    private var etSearch: String? = null
    private lateinit var cursor: Cursor

    override fun getViewBinding() = ActivitySearchBinding.inflate(layoutInflater)

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun initView() {
        binding.rvSearchMusic.layoutManager = LinearLayoutManager(this)
        binding.rvSearchVideo.layoutManager = LinearLayoutManager(this)

        helper = RecordSQLiteOpenHelper(this)
        cursor = queryData(this, helper!!, "")
        showHistory()
    }

    private fun showHistory(){
        val adapter = SimpleCursorAdapter(
            this,
            R.layout.simple_list_item_1,
            cursor,
            arrayOf("name"),
            intArrayOf(R.id.text1),
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )

        binding.listView.adapter = adapter
        adapter.notifyDataSetChanged()

        if (cursor.count != 0) {
            binding.tvClear.visibility = View.VISIBLE
        } else {
            binding.tvClear.visibility = View.GONE
        }
    }

    override fun initData() {
        commonPresenter = CommonPresenter(this)
    }

    override fun initListener() {
        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.llSearchMusicMore.setOnClickListener {
            val intent = Intent(this, MusicActivity::class.java)
            val bundle = Bundle()
            bundle.putString(Const.SEARCH, etSearch)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        binding.llSearchVideoMore.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            val bundle = Bundle()
            bundle.putString(Const.SEARCH, etSearch)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val tempName: String = binding.etSearch.text.toString()
                queryData(
                    this@SearchActivity,
                    helper!!,
                    tempName
                )
            }
        })

        binding.etSearch.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                etSearch = binding.etSearch.text.toString().trim { it <= ' ' }
                mCallBack?.SearchAciton(binding.etSearch.text.toString())
                val hasData = hasData(helper!!, binding.etSearch.text.toString().trim())
                if (!hasData) {
                    insertData(helper!!, binding.etSearch.text.toString().trim())
                    queryData(this@SearchActivity, helper!!, "")
                }
                getData()
            }
            false
        }

        binding.listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val textView: TextView = view.findViewById<View>(android.R.id.text1) as TextView
                val name: String = textView.text.toString()
                binding.etSearch.setText(name)
                etSearch = name
                getData()
            }

        binding.tvClear.setOnClickListener {
            deleteData(helper!!)
            binding.listView.visibility = View.GONE
            binding.tvClear.visibility=View.GONE
        }
    }

    override fun onClick(v: View?) {
    }

    private fun getData() {
        commonPresenter?.getMusic(etSearch!!, Const.DEFAULT_LIMIT, Const.DEFAULT_OFFSET)
        commonPresenter?.getVideo(etSearch!!, Const.DEFAULT_LIMIT, Const.DEFAULT_OFFSET)
        hideKeyboard()
    }

    @SuppressLint("SetTextI18n")
    override fun onHttpSuccess(reqType: Int, msg: Message) {
        binding.llSearchHistory.visibility = View.GONE
        if (reqType == IHttpService.HTTP_GET_MUSIC) {
            binding.llSearchMusicNoHistory.visibility = View.GONE
            binding.tvSearchMusic.visibility = View.VISIBLE
            binding.llSearchMusicMore.visibility = View.VISIBLE
            binding.rvSearchMusic.visibility = View.VISIBLE
            val musicBean = GsonUtil.gsonToBean(msg.obj as String, Music::class.java)
            if (musicBean.results.isNotEmpty()) {
                val searchMusicRvAdapter =
                    MusicRvQuickAdapter(musicBean.results)
                binding.rvSearchMusic.adapter = searchMusicRvAdapter
            } else {
                binding.llSearchMusicNoHistory.visibility = View.VISIBLE
                binding.rvSearchMusic.visibility = View.GONE
            }
        }

        if (reqType == IHttpService.HTTP_GET_VIDEO) {
            binding.llSearchVideoNoHistory.visibility = View.GONE
            binding.tvSearchVideo.visibility = View.VISIBLE
            binding.llSearchVideoMore.visibility = View.VISIBLE
            binding.rvSearchVideo.visibility = View.VISIBLE
            val videoBean = GsonUtil.gsonToBean(msg.obj as String, Video::class.java)
            if (videoBean.results.isNotEmpty()) {
                val videoRvAdapter =
                    VideoRvQuickAdapter(videoBean.results)
                binding.rvSearchVideo.adapter = videoRvAdapter
            } else {
                binding.llSearchVideoNoHistory.visibility = View.VISIBLE
                binding.rvSearchVideo.visibility = View.GONE
            }
        }
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