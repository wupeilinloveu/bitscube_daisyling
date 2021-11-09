package com.example.daisyling.ui.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.daisyling.R
import com.example.daisyling.common.util.Utils
import com.example.daisyling.common.util.download.DownloadListener
import com.example.daisyling.common.util.download.DownloadUtil
import com.example.daisyling.common.util.download.InputParameter
import com.example.daisyling.db.Track
import com.example.daisyling.db.TrackDatabase
import com.example.daisyling.model.bean.MusicResult
import com.example.local_music.ui.view.main.CommonMusicActivity
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.squareup.picasso.Picasso
import java.io.File
import kotlin.concurrent.thread

/**
 * Created by Emily on 9/30/21
 */
class MusicRvQuickAdapter(data: MutableList<MusicResult>?) :
    BaseQuickAdapter<MusicResult, BaseViewHolder>(R.layout.common_rv_item, data) {
    private var desFilePath: String? = null
    private var myFile: File? = null

    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewHolder, item: MusicResult) {
        val mCommonLlItem =
            holder.getView<LinearLayout>(R.id.common_ll_item)
        val mCommonIvIcon =
            holder.getView<ImageView>(R.id.common_iv_icon)
        val mCommonTvTitle =
            holder.getView<TextView>(R.id.common_tv_title)
        val mCommonTvContent =
            holder.getView<TextView>(R.id.common_tv_content)
        val mCommonImgMoreVert =
            holder.getView<ImageView>(R.id.common_img_more_vert)
        val mCommonProgress =
            holder.getView<ProgressBar>(R.id.common_progress)

        Picasso.get().load(item.artworkUrl100)
            .transform(com.example.daisyling.ui.widget.CircleCornerForm()).into(mCommonIvIcon)
        val name = item.trackName
        mCommonTvTitle.text = name
        mCommonTvContent.text =
            item.artistName + "," + item.trackCensoredName

        val trackId = item.trackId
        val artworkUrl100 = item.artworkUrl100
        val trackName = item.trackName
        val artistName = item.artistName
        val trackCensoredName = item.trackCensoredName
        val url = item.previewUrl

        val baseUrl = url.substring(0, 34)
        val fileUrl = url.substring(34)
        mCommonLlItem.setOnClickListener {

//            val intent = Intent(context, MusicDetailActivity::class.java)
//            val bundle = Bundle()
//            bundle.putInt(Const.TRACk_ID,trackId)
//            bundle.putString(Const.ARTWORK_URL,artworkUrl100)
//            bundle.putString(Const.TRACK_NAME,trackName)
//            bundle.putString(Const.ARTIST_NAME,artistName)
//            bundle.putString(Const.TRACK_CENSORED_NAME,trackCensoredName)
//            bundle.putString(Const.PREVIEW_URL,url)
//            intent.putExtras(bundle)
//            context.startActivity(intent)

            val intent = Intent(context, CommonMusicActivity::class.java)
            val bundle = Bundle()
            intent.putExtras(bundle)
            context.startActivity(intent)

//            if (myFile.toString().contains(name)) {
//                Utils.installFile(context, myFile!!, "audio/mp4a-latm")
//                Utils.openLocalFile(context,myFile.toString())
//            } else {
//                mCommonProgress.visibility = View.VISIBLE
//                desFilePath = context.getExternalFilesDir(null)!!.absolutePath + "/" + "$name"
//                startDownload(
//                    trackId, artworkUrl100, trackName,
//                    artistName, trackCensoredName, url,
//                    baseUrl, fileUrl, desFilePath, mCommonProgress
//                )
//            }
        }

        mCommonImgMoreVert.setOnClickListener {
            showBottomDialog(
                context, trackId, artworkUrl100, trackName,
                artistName, trackCensoredName, url,
                baseUrl, fileUrl, mCommonProgress
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showBottomDialog(
        context: Context, trackId: Int,
        artworkUrl100: String,
        trackName: String,
        artistName: String,
        trackCensoredName: String,
        previewUrl: String,
        baseUrl: String,
        fileUrl: String, progress: ProgressBar
    ) {
        val buttonDialog: Dialog = Dialog(context, R.style.BottomDialog)
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.common_bottom_dialog, null)
        val mTvName: TextView = view.findViewById(R.id.tv_name)
        val mTvDownLoad: TextView = view.findViewById(R.id.tv_download)
        val mTvFavorite: TextView = view.findViewById(R.id.tv_favorite)
        val mTvShare: TextView = view.findViewById(R.id.tv_share)
        val mTvAlert: TextView = view.findViewById(R.id.tv_alert)
        val mTvDelete: TextView = view.findViewById(R.id.tv_delete)
        mTvName.text = context.getString(R.string.music_name) + trackName

        buttonDialog.setContentView(view)
        val layoutParams: ViewGroup.MarginLayoutParams =
            view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width = context.resources.displayMetrics.widthPixels - DensityUtil.dp2px(16f)
        layoutParams.bottomMargin = DensityUtil.dp2px(8f)
        view.layoutParams = layoutParams
        buttonDialog.setCanceledOnTouchOutside(true)
        buttonDialog.window?.setGravity(Gravity.BOTTOM)
        buttonDialog.show()

        mTvDownLoad.setOnClickListener {
            buttonDialog.dismiss()
            progress.visibility = View.VISIBLE
            desFilePath = context.getExternalFilesDir(null)!!.absolutePath + "/" + "$trackName"
            startDownload(
                trackId, artworkUrl100, trackName,
                artistName, trackCensoredName, previewUrl,
                baseUrl, fileUrl, desFilePath, progress
            )
        }

        mTvFavorite.setOnClickListener {
            buttonDialog.dismiss()
            Utils.showToast(context, context.getString(R.string.favorite_success))
        }

        mTvShare.setOnClickListener {
            buttonDialog.dismiss()
            Utils.showToast(context, context.getString(R.string.share_success))
        }

        mTvAlert.setOnClickListener {
            buttonDialog.dismiss()
            Utils.showToast(context, context.getString(R.string.alert_success))
        }

        mTvDelete.setOnClickListener {
            buttonDialog.dismiss()
            Utils.showToast(context, context.getString(R.string.develop_tip))
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
                InputParameter.Builder(baseUrl, fileUrl, desFilePath!!)
                    .setCallbackOnUiThread(true)
                    .build(), object : DownloadListener {
                    @SuppressLint("SetTextI18n")
                    override fun onFinish(file: File?) {
                        mProgress.visibility = View.GONE
                        myFile = file
//                        Utils.installFile(context, file, "audio/mp4a-latm")
//                        Utils.openLocalFile(context, myFile.toString())
                        //Insert download data
                        val musicDao= TrackDatabase.getDatabase(context).trackDao()
                        thread {
                            val track = Track(
                                trackId = trackId.toString(),
                                artworkUrl100 = artworkUrl100,
                                trackName = trackName,
                                artistName = artistName,
                                trackCensoredName = trackCensoredName,
                                previewUrl = previewUrl
                            )
                            musicDao.insertTrack(track)
                        }
                    }

                    override fun onProgress(
                        progress: Int,
                        downloadedLengthKb: Long,
                        totalLengthKb: Long
                    ) {
                        mProgress.progress = progress
                    }

                    override fun onFailed(errMsg: String?) {
                        mProgress.visibility = View.GONE
                        Utils.showToast(context, errMsg!!)
                    }
                })
    }

}