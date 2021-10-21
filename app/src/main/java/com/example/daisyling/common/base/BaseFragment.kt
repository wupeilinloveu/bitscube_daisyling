package com.example.daisyling.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.daisyling.common.util.LoadingDialogUtil
import me.yokeyword.fragmentation.SupportFragment

/**
 * Encapsulate Fragment To find child controls，set listeners, initialize data.
 * Created by Emily on 9/30/21
 */
abstract class BaseFragment<VB : ViewBinding> : SupportFragment(), IUIOperation, BaseView {
    private var loading: LoadingDialogUtil? = null

    private lateinit var _binding: VB
    protected val binding get() = _binding

    override fun onStop() {
        super.onStop()
        if (loading != null) {
            if (loading!!.isShowing()) {
                loading!!.dismiss()
                loading = null
            }
        }
    }

    open fun showLoading() {
        loading = activity?.let { LoadingDialogUtil(it) }
        loading?.show()
    }

    open fun dismissLoading() {
        loading?.dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getBinding(inflater, container)
        initView()
        initData()
        initListener()
        return _binding.root
    }

    protected abstract fun getBinding(inflater: LayoutInflater, viewGroup: ViewGroup?): VB
}

