package com.example.daisyling.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

/**
 * Created by Emily on 9/30/21
 */
object Utils {
    /**
     * Show Toast
     */
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Install File
     */
    fun installFile(context: Context, file: File, type: String) {
        val authority = "com.example.daisyling.ui.activity.FileProvider"
        val intent = Intent(Intent.ACTION_VIEW)
        val data: Uri
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file)
        } else {
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            data = FileProvider.getUriForFile(context, authority, file)

        }
        intent.setDataAndType(data, type)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    /**
     * Open local file
     */
    fun openLocalFile(context: Context, path: String) {
        try {
            val file = File(path)
            var uri: Uri? = null
            uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(context, context.packageName + ".fileProvider", file)
            } else {
                Uri.fromFile(file)
            }
            context.grantUriPermission(
                context.packageName,
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            val intent2 = Intent("android.intent.action.VIEW")
            intent2.addCategory("android.intent.category.DEFAULT")
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

            val type = AndroidFileUtil.getMIMEType(path)
            intent2.setDataAndType(uri, type)
            context.startActivity(intent2)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

