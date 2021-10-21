package com.example.daisyling.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.daisyling.R
import com.example.daisyling.common.util.Utils
import com.example.daisyling.common.util.download.DownloadListener
import com.example.daisyling.common.util.download.DownloadUtil
import com.example.daisyling.common.util.download.InputParameter
import com.example.daisyling.db.AnkoSQLiteManager
import com.example.daisyling.db.User
import com.example.daisyling.model.bean.MusicResult
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import java.io.File


/**
 * Created by Emily on 9/30/21
 */
class MusicRvQuickAdapter(data: MutableList<MusicResult>?) :
    BaseQuickAdapter<MusicResult, BaseViewHolder>(R.layout.common_rv_item, data) {
    private var desFilePath: String? = null
    private var myFile: File? = null

    @SuppressLint("SetTextI18n")
    override fun convert(baseViewHolder: BaseViewHolder, list: MusicResult) {
        val mCommonRlItem =
            baseViewHolder.getView<RelativeLayout>(R.id.common_rl_item)
        val mCommonIvIcon =
            baseViewHolder.getView<ImageView>(R.id.common_iv_icon)
        val mCommonTvTitle =
            baseViewHolder.getView<TextView>(R.id.common_tv_title)
        val mCommonTvContent =
            baseViewHolder.getView<TextView>(R.id.common_tv_content)
        val mCommonProgress =
            baseViewHolder.getView<ProgressBar>(R.id.common_progress)

        Picasso.get().load(list.artworkUrl100)
            .transform(com.example.daisyling.ui.view.CircleCornerForm()).into(mCommonIvIcon)
        val name = list.trackName
        mCommonTvTitle.text = name
        mCommonTvContent.text =
            list.artistName + "," + list.trackCensoredName

        val trackId= list.trackId
        val artworkUrl100=list.artworkUrl100
        val trackName=list.trackName
        val artistName=list.artistName
        val trackCensoredName=list.trackCensoredName
        val url = list.previewUrl

        val baseUrl = url.substring(0, 34)
        val fileUrl = url.substring(34)
        mCommonRlItem.setOnClickListener {
            if (myFile.toString().contains(name)) {
                Utils.installFile(context, myFile!!, "audio/mp4a-latm")
                Utils.openLocalFile(myFile.toString(), context)
            } else {
                mCommonProgress.visibility = View.VISIBLE
                desFilePath = context.getExternalFilesDir(null)!!.absolutePath + "/" + "$name"
                startDownload(
                    trackId, artworkUrl100, trackName,
                    artistName, trackCensoredName, url,
                    baseUrl, fileUrl, desFilePath, mCommonProgress
                )
            }
        }
    }

    private fun startDownload(
        trackId: Int,
        artworkUrl100: String,
        trackName: String,
        artistName: String,
        trackCensoredName: String,
        previewUrl: String,
        baseUrl: String,
        fileUrl: String,
        desFilePath: String?,
        mProgress: ProgressBar
    ) {
        DownloadUtil.getInstance()
            .downloadFile(
                InputParameter.Builder(baseUrl, fileUrl, desFilePath)
                    .setCallbackOnUiThread(true)
                    .build(), object : DownloadListener {
                    @SuppressLint("SetTextI18n")
                    override fun onFinish(file: File) {
                        mProgress.visibility = View.GONE
                        myFile = file
                        Utils.installFile(context, file, "audio/mp4a-latm")
                        Utils.openLocalFile(myFile.toString(), context)
                        //Insert data
                        doAsync {
                            val user = User(
                                user_id = trackId.toString(),
                                artworkUrl100 = artworkUrl100,
                                trackName = trackName,
                                artistName = artistName,
                                trackCensoredName = trackCensoredName,
                                previewUrl = previewUrl
                            )
                            AnkoSQLiteManager().insertUser(user)
                        }
                    }

                    override fun onProgress(
                        progress: Int,
                        downloadedLengthKb: Long,
                        totalLengthKb: Long
                    ) {
                        mProgress.progress = progress
                    }

                    override fun onFailed(errMsg: String) {
                    }
                })
    }

}