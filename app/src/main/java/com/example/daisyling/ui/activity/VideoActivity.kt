package com.example.daisyling.ui.activity

import android.os.Message
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daisyling.common.base.BaseActivity
import com.example.daisyling.common.base.Const
import com.example.daisyling.common.util.GsonUtil
import com.example.daisyling.common.util.LogUtil
import com.example.daisyling.databinding.ActivityVideoBinding
import com.example.daisyling.model.bean.Video
import com.example.daisyling.model.protocol.IHttpService
import com.example.daisyling.presenter.CommonPresenter
import com.example.daisyling.ui.adapter.VideoRvQuickAdapter
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener

/**
 * Created by Emily on 10/13/21
 */
class VideoActivity : BaseActivity<ActivityVideoBinding>() {
    private var page: Int = 1
    private var adapter: VideoRvQuickAdapter? = null
    private var commonPresenter: CommonPresenter? = null
    private var etSearch: String? = null

    override fun getViewBinding() = ActivityVideoBinding.inflate(layoutInflater)

    override fun initView() {
        val bundle = this.intent.extras
        etSearch = bundle?.getString(Const.SEARCH, Const.DEFAULT_VIDEO_TERM)
        binding.rvVideo.layoutManager = LinearLayoutManager(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.refreshVideo.setRefreshHeader(MaterialHeader(this))
        binding.refreshVideo.isEnableRefresh = false
        binding.refreshVideo.isEnableLoadMore = true
        binding.refreshVideo.setDisableContentWhenRefresh(true)
        binding.refreshVideo.setDisableContentWhenLoading(true)

        binding.refreshVideo.setOnLoadMoreListener(OnLoadMoreListener { refreshlayout ->
            refreshlayout.finishLoadMore(1500, false, false)
            page++
            val offset = Const.MEDIA_LIMIT * page
            LogUtil.d("page:" + "$page")
            LogUtil.d("offset:" + "$offset")
            commonPresenter?.getVideo(etSearch!!, Const.MEDIA_LIMIT, Const.MEDIA_LIMIT * page)
        })
    }

    override fun initData() {
        showLoading()
        commonPresenter = CommonPresenter(this)
        commonPresenter?.getVideo(etSearch!!, Const.MEDIA_LIMIT, Const.MEDIA_OFFSET)
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
        if (reqType == IHttpService.HTTP_GET_VIDEO) {
            val videoBean = GsonUtil.gsonToBean(msg.obj as String, Video::class.java)
            if (videoBean.results.isNotEmpty()) {
                binding.refreshVideo.visibility = View.VISIBLE
                if (page == 1) {
                    if (adapter == null) {
                        adapter =
                            VideoRvQuickAdapter(videoBean.results)
                        binding.rvVideo.adapter = adapter
                    }
                } else {
                    if (videoBean.results.size > 0) {
                        adapter!!.addData(videoBean.results)
                    } else {
                        binding.llVideoBottom.visibility = View.VISIBLE
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