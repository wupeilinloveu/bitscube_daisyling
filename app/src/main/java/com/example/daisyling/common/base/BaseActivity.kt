package com.example.daisyling.common.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.viewbinding.ViewBinding
import com.example.daisyling.common.util.LoadingDialogUtil
import me.yokeyword.fragmentation.SupportActivity

/**
 * Encapsulate Activity To find child controls，set listeners, initialize data.
 * Created by Emily on 9/30/21
 */
@SuppressLint("SourceLockedOrientationActivity", "HandlerLeak")
abstract class BaseActivity<VB : ViewBinding> : SupportActivity(), IUIOperation, BaseView {
    private var loading: LoadingDialogUtil? = null

    private lateinit var _binding: VB
    protected val binding get() = _binding

    open fun showLoading() {
        loading = LoadingDialogUtil(this)
        loading!!.show()
    }

    open fun dismissLoading() {
        loading!!.dismiss()
    }

    override fun onStop() {
        super.onStop()
        if (loading != null) {
            if (loading!!.isShowing) {
                loading!!.dismiss()
                loading = null
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(binding.root)
        initView()
        initData()
        initListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding == null
    }

    protected abstract fun getViewBinding(): VB

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                val v = currentFocus
                if (isShouldHideInput(v, ev)) {
                    hideSoftInput(v?.windowToken)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = (left
                    + v.getWidth())
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        return false
    }

    private fun hideSoftInput(token: IBinder?) {
        if (token != null) {
            val im = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(
                token,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}
