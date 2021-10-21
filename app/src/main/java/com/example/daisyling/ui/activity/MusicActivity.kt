package com.example.daisyling.ui.activity

import android.os.Message
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daisyling.common.base.BaseActivity
import com.example.daisyling.common.base.Const
import com.example.daisyling.common.util.GsonUtil
import com.example.daisyling.common.util.LogUtil
import com.example.daisyling.databinding.ActivityMusicBinding
import com.example.daisyling.model.bean.Music
import com.example.daisyling.model.protocol.IHttpService
import com.example.daisyling.presenter.CommonPresenter
import com.example.daisyling.ui.adapter.MusicRvQuickAdapter
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener

/**
 * Created by Emily on 10/13/21
 */
class MusicActivity : BaseActivity<ActivityMusicBinding>() {
    private var page: Int = 1
    private var adapter: MusicRvQuickAdapter? = null
    private var commonPresenter: CommonPresenter? = null
    private var etSearch: String? = null

    override fun getViewBinding() = ActivityMusicBinding.inflate(layoutInflater)

    override fun initView() {
        val bundle = this.intent.extras
        etSearch = bundle?.getString(Const.SEARCH, Const.DEFAULT_MUSIC_TERM)
        LogUtil.d(etSearch!!)

        binding.rvMusic.layoutManager = LinearLayoutManager(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.refreshMusic.setRefreshHeader(MaterialHeader(this))
        binding.refreshMusic.isEnableRefresh = false
        binding.refreshMusic.isEnableLoadMore = true
        binding.refreshMusic.setDisableContentWhenRefresh(true)
        binding.refreshMusic.setDisableContentWhenLoading(true)

        binding.refreshMusic.setOnLoadMoreListener(OnLoadMoreListener { refreshlayout ->
            refreshlayout.finishLoadMore(1500, false, false)
            page++
            commonPresenter?.getMusic(etSearch!!, Const.MEDIA_LIMIT, Const.MEDIA_LIMIT * page)
        })
    }

    override fun initData() {
        showLoading()
        commonPresenter = CommonPresenter(this)
        commonPresenter?.getMusic(etSearch!!, Const.MEDIA_LIMIT, Const.MEDIA_OFFSET)
    }

    override fun initListener() {
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    override fun onClick(v: View?) {
    }

    override fun onHttpSuccess(reqType: Int, msg: Message) {
        dismissLoading()
        if (reqType == IHttpService.HTTP_GET_MUSIC) {
            val musicBean = GsonUtil.gsonToBean(msg.obj as String, Music::class.java)
            if (musicBean.results.isNotEmpty()) {
                binding.refreshMusic.visibility = View.VISIBLE
                if (page == 1) {
                    if (adapter == null) {
                        adapter =
                            MusicRvQuickAdapter(musicBean.results)
                        binding.rvMusic.adapter = adapter
                    }
                } else {
                    if (musicBean.results.size > 0) {
                        adapter!!.addData(musicBean.results)
                    } else {
                        binding.llMusicBottom.visibility = View.VISIBLE
                    }
                }

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