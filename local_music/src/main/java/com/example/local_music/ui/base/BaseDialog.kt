package com.example.local_music.ui.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import com.example.local_music.R

/**
 * Created by Emily on 11/1/21
 */
abstract class BaseDialog(var mContext: Context, theme: Int = R.style.lm_dialog_base) :
    Dialog(mContext, theme) {
    protected var view: View? = null

    fun init() {
        val inflater = LayoutInflater.from(mContext)
        view = inflater.inflate(layoutId, null)

        view?.let { this.setContentView(it) }

        val backIv = view?.findViewById<ImageView>(R.id.lm_main_back_iv)
        backIv?.setOnClickListener { cancel() }

        initView()

        initData()

        setCanceledOnTouchOutside(false)
    }

    protected abstract val layoutId: Int
    protected abstract fun initView()
    protected fun initData() {}

    override fun show() {
        view ?: init()
        val lp = window?.attributes
        lp?.width = context.resources.getDimensionPixelSize(R.dimen.lm_dialog_width)
        lp?.gravity = Gravity.CENTER

        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        super.show()
    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        super.setOnDismissListener(listener)
    }

    override fun cancel() {
        super.cancel()
    }

    protected fun showMsg(msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun changeView(loginView: BaseDialog) {
        loginView.show()
    }

}