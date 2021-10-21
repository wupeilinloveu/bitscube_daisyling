package com.example.daisyling.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.daisyling.R
import com.example.daisyling.common.util.Utils
import com.example.daisyling.common.util.download.DownloadListener
import com.example.daisyling.common.util.download.DownloadUtil
import com.example.daisyling.common.util.download.InputParameter
import com.example.daisyling.db.User
import com.squareup.picasso.Picasso
import java.io.File

/**
 * Created by Emily on 10/20/21
 */
class CommonLvAdapter(val context: Context, var data: List<User>) : BaseAdapter() {

    private var desFilePath: String? = null
    private var myFile: File? = null

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vh: ViewHolder
        val view: View
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.common_rv_item, parent, false);
            vh = ViewHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolder
        }

        Picasso.get().load(data[position].artworkUrl100)
            .transform(com.example.daisyling.ui.view.CircleCornerForm())!!.into(vh.mCommonIvIcon)
        val name = data[position].trackName
        vh.mCommonTvTitle.text = name
        vh.mCommonTvContent.text =
            data[position].artistName + "," + data[position].trackCensoredName

        val baseUrl = data[position].previewUrl?.substring(0, 34)
        val fileUrl = data[position].previewUrl?.substring(34)
        vh.mCommonRlItem.setOnClickListener {
           vh.mCommonProgress.visibility = View.VISIBLE
            desFilePath = context.getExternalFilesDir(null)!!.absolutePath + "/" + "$name"
            startDownload( baseUrl!!, fileUrl!!, desFilePath,vh.mCommonProgress)
        }
        return view
    }

    inner class ViewHolder(v: View) {
        val mCommonRlItem: RelativeLayout = v.findViewById(R.id.common_rl_item)
        val mCommonIvIcon: ImageView = v.findViewById(R.id.common_iv_icon)
        val mCommonTvTitle: TextView = v.findViewById(R.id.common_tv_title)
        val mCommonTvContent: TextView = v.findViewById(R.id.common_tv_content)
        val mCommonProgress: ProgressBar = v.findViewById(R.id.common_progress)
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data.size
    }

    private fun startDownload(
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
