package com.example.daisyling.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daisyling.common.base.BaseFragment
import com.example.daisyling.common.base.Const
import com.example.daisyling.common.util.GsonUtil
import com.example.daisyling.databinding.FragmentHomeBinding
import com.example.daisyling.model.bean.Music
import com.example.daisyling.model.bean.Video
import com.example.daisyling.model.protocol.IHttpService
import com.example.daisyling.presenter.CommonPresenter
import com.example.daisyling.ui.activity.MusicActivity
import com.example.daisyling.ui.activity.SearchActivity
import com.example.daisyling.ui.activity.VideoActivity
import com.example.daisyling.ui.adapter.MusicRvQuickAdapter
import com.example.daisyling.ui.adapter.VideoRvQuickAdapter

/**
 * Created by Emily on 9/30/21
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private var commonPresenter: CommonPresenter? = null

    override fun getBinding(inflater: LayoutInflater, viewGroup: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, viewGroup, false)

    override fun initView() {
        binding.rvSearchMusic.layoutManager = LinearLayoutManager(_mActivity)
        binding.rvSearchVideo.layoutManager = LinearLayoutManager(_mActivity)
    }

    override fun initData() {
        showLoading();
        commonPresenter = CommonPresenter(this)
        commonPresenter?.getMusic(Const.DEFAULT_MUSIC_TERM, Const.DEFAULT_LIMIT, Const.DEFAULT_OFFSET)
        commonPresenter?.getVideo(Const.DEFAULT_VIDEO_TERM, Const.DEFAULT_LIMIT, Const.DEFAULT_OFFSET)
    }

    override fun initListener() {
        binding.llSearch.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            val bundle = Bundle()
            intent.putExtras(bundle)
            context?.startActivity(intent)
        }

       binding.tvSearchMusicMore.setOnClickListener {
           val intent = Intent(context, MusicActivity::class.java)
           val bundle = Bundle()
           intent.putExtras(bundle)
           context?.startActivity(intent)
       }

        binding.tvSearchVideoMore.setOnClickListener {
            val intent = Intent(context, VideoActivity::class.java)
            val bundle = Bundle()
            intent.putExtras(bundle)
            context?.startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
    }

    override fun onHttpSuccess(reqType: Int, msg: Message) {
        dismissLoading();
        if (reqType == IHttpService.HTTP_GET_MUSIC) {
            val musicBean = GsonUtil.gsonToBean(msg.obj as String, Music::class.java)
            if (musicBean.results.isNotEmpty()) {
                binding.llSearchMusic.visibility=View.VISIBLE
                val searchMusicRvAdapter =
                    MusicRvQuickAdapter(musicBean.results)
                binding.rvSearchMusic.adapter = searchMusicRvAdapter
            }
        }

        if (reqType == IHttpService.HTTP_GET_VIDEO) {
            val videoBean = GsonUtil.gsonToBean(msg.obj as String, Video::class.java)
            if (videoBean.results.isNotEmpty()) {
                binding.llSearchVideo.visibility=View.VISIBLE
                val videoRvAdapter =
                    VideoRvQuickAdapter(videoBean.results)
                binding.rvSearchVideo.adapter = videoRvAdapter
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