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
import androidx.recyclerview.widget.RecyclerView
import com.example.daisyling.R
import com.example.daisyling.common.util.Utils
import com.example.daisyling.common.util.download.DownloadListener
import com.example.daisyling.common.util.download.DownloadUtil
import com.example.daisyling.common.util.download.InputParameter
import com.example.daisyling.db.AppDatabase
import com.example.daisyling.db.TrackEntity
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.squareup.picasso.Picasso
import java.io.File
import kotlin.concurrent.thread

/**
 * Created by Emily on 10/26/21
 */
class TrackDbRvAdapter(
    var context: Context,
    private val list: MutableList<TrackEntity>?,
    type: String
) :
    RecyclerView.Adapter<TrackDbRvAdapter.ViewHolder>() {
    private var _type: String = type
    private var desFilePath: String? = null
    private var myFile: File? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.common_rv_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            Picasso.get().load(list!![position].artworkUrl100)
                .transform(com.example.daisyling.ui.widget.CircleCornerForm())
                .into(holder.mCommonIvIcon)
            val name = list[position].trackName
            holder.mCommonTvTitle.text = name
            holder.mCommonTvContent.text =
                list[position].artistName + "," + list[position].trackCensoredName

            val trackId = list[position].trackId
            val artworkUrl100 = list[position].artworkUrl100
            val trackName = list[position].trackName
            val artistName = list[position].artistName
            val trackCensoredName = list[position].trackCensoredName
            val url = list[position].previewUrl

            val baseUrl = url!!.substring(0, 34)
            val fileUrl = url.substring(34)
            holder.mCommonLlRv.setOnClickListener {
                if (myFile.toString().contains(name!!)) {
                    Utils.installFile(context, myFile!!, _type)
                    Utils.openLocalFile(context, myFile.toString())
                } else {
                    holder.mCommonProgress.visibility = View.VISIBLE
                    desFilePath = context.getExternalFilesDir(null)!!.absolutePath + "/" + "$name"
                    startDownload(
                        trackId, artworkUrl100!!, trackName!!,
                        artistName!!, trackCensoredName!!, url,
                        baseUrl, fileUrl, desFilePath, holder.mCommonProgress
                    )
                }
            }

            holder.mCommonImgMoreVert.setOnClickListener {
                showBottomDialog(context, trackId, trackName!!, position)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    @SuppressLint("SetTextI18n")
    private fun showBottomDialog(
        context: Context,
        trackId: String,
        trackName: String,
        position: Int
    ) {
        val buttonDialog: Dialog = Dialog(context, R.style.BottomDialog)
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.common_bottom_dialog, null)
        val mTvName: TextView = view.findViewById(R.id.tv_name)
        val mTvDownLoad: TextView = view.findViewById(R.id.tv_download)
        val mTvFavorite: TextView = view.findViewById(R.id.tv_favorite)
        val mTvDelete: TextView = view.findViewById(R.id.tv_delete)
        mTvName.text = trackName

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
            Utils.showToast(context, context.getString(R.string.download_success))
        }

        mTvFavorite.setOnClickListener {
            buttonDialog.dismiss()
            Utils.showToast(context, context.getString(R.string.favorite_success))
        }

        mTvDelete.setOnClickListener {
            buttonDialog.dismiss()
            //Delete data
            val myDownLoadDao = AppDatabase.getDatabase(context).myDownLoadDao()
            thread {
                myDownLoadDao.deleteTrackByTrackId(trackId)
            }
            list!!.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
            Utils.showToast(context, context.getString(R.string.delete_success))
        }
    }

    private fun startDownload(
        trackId: String,
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
//                        mProgress.visibility = View.GONE
                        myFile = file
                        Utils.installFile(context, file!!, _type)
                        Utils.openLocalFile(context, myFile.toString())
                        //Insert download data
                        val myDownLoadDao = AppDatabase.getDatabase(context).myDownLoadDao()
                        thread {
                            val track = TrackEntity(
                                trackId = trackId.toString(),
                                artworkUrl100 = artworkUrl100,
                                trackName = trackName,
                                artistName = artistName,
                                trackCensoredName = trackCensoredName,
                                previewUrl = previewUrl
                            )
                            myDownLoadDao.insertTrack(track)
                        }
                    }

                    override fun onProgress(
                        progress: Int,
                        downloadedLengthKb: Long,
                        totalLengthKb: Long
                    ) {
//                        mProgress.progress = progress
                    }

                    override fun onFailed(errMsg: String?) {
                        mProgress.visibility = View.GONE
                        Utils.showToast(context, errMsg!!)
                    }
                })
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mCommonLlRv: LinearLayout =
            view.findViewById(R.id.common_ll_item)
        val mCommonIvIcon: ImageView =
            view.findViewById(R.id.common_iv_icon)
        val mCommonTvTitle: TextView =
            view.findViewById(R.id.common_tv_title)
        val mCommonTvContent: TextView =
            view.findViewById(R.id.common_tv_content)
        val mCommonImgMoreVert: ImageView =
            view.findViewById(R.id.common_img_more_vert)
        val mCommonProgress: ProgressBar =
            view.findViewById(R.id.common_progress)
    }
}