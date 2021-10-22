package com.example.daisyling.ui.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
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
import com.example.daisyling.db.AnkoSQLiteManager
import com.example.daisyling.db.User
import com.example.daisyling.model.bean.VideoResult
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import java.io.File

/**
 * Created by Emily on 9/30/21
 */
class VideoRvQuickAdapter(data: MutableList<VideoResult>?) :
    BaseQuickAdapter<VideoResult, BaseViewHolder>(R.layout.common_rv_item, data) {
    private var desFilePath: String? = null
    private var myFile: File? = null

    @SuppressLint("SetTextI18n")
    override fun convert(baseViewHolder: BaseViewHolder, list: VideoResult) {
        val mCommonLlItem =
            baseViewHolder.getView<LinearLayout>(R.id.common_ll_item)
        val mCommonIvIcon =
            baseViewHolder.getView<ImageView>(R.id.common_iv_icon)
        val mCommonTvTitle =
            baseViewHolder.getView<TextView>(R.id.common_tv_title)
        val mCommonTvContent =
            baseViewHolder.getView<TextView>(R.id.common_tv_content)
        val mCommonImgMoreVert =
            baseViewHolder.getView<ImageView>(R.id.common_img_more_vert)
        val mCommonProgress =
            baseViewHolder.getView<ProgressBar>(R.id.common_progress)

        Picasso.get().load(list.artworkUrl100)
            .transform(com.example.daisyling.ui.view.CircleCornerForm()).into(mCommonIvIcon)
        val name = list.trackName
        mCommonTvTitle.text = name
        mCommonTvContent.text =
            list.artistName + "," + list.trackCensoredName

        val trackId = list.trackId
        val artworkUrl100 = list.artworkUrl100
        val trackName = list.trackName
        val artistName = list.artistName
        val trackCensoredName = list.trackCensoredName
        val url = list.previewUrl

        val baseUrl = url.substring(0, 34)
        val fileUrl = url.substring(34)
        mCommonLlItem.setOnClickListener {
            if (myFile.toString().contains(name)) {
                Utils.installFile(context, myFile!!, "video/x-m4v")
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
            LayoutInflater.from(context).inflate(R.layout.common_dialog_content_circle, null)
        val mTvName: TextView = view.findViewById(R.id.tv_name)
        val mTvDownLoad: TextView = view.findViewById(R.id.tv_download)
        val mTvFavorite: TextView = view.findViewById(R.id.tv_favorite)
        val mTvShare: TextView = view.findViewById(R.id.tv_share)
        val mTvAlert: TextView = view.findViewById(R.id.tv_alert)
        val mTvDelete: TextView = view.findViewById(R.id.tv_delete)
        mTvName.text = context.getString(R.string.video_name) + trackName

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
                InputParameter.Builder(baseUrl, fileUrl, desFilePath)
                    .setCallbackOnUiThread(true)
                    .build(), object : DownloadListener {
                    @SuppressLint("SetTextI18n")
                    override fun onFinish(file: File) {
                        mProgress.visibility = View.GONE
                        myFile = file
                        Utils.installFile(context, file, "video/x-m4v")
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