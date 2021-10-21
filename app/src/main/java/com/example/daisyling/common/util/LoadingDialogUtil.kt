package com.example.daisyling.common.util

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.widget.ImageView
import com.example.daisyling.R

/**
 * Created by Emily on 9/30/21
 */
class LoadingDialogUtil(context: Context) : Dialog(context, R.style.CustomProgressDialog) {
    private var imgIv: ImageView? = null

    init {
        setContentView(R.layout.common_loading)
        imgIv = findViewById<ImageView>(R.id.imgIv)
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun initAnim() {
        val animator = ObjectAnimator.ofFloat(imgIv, "rotation", 0f, 359f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.duration = 2000
        animator.start()
    }

    override fun show() {
        super.show()
        initAnim()
    }

    override fun dismiss() {
        super.dismiss()
    }

}


