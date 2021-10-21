package com.example.daisyling.presenter

import android.os.Message
import com.example.daisyling.common.base.BaseView
import com.example.daisyling.model.protocol.BaseProtocol
import com.example.daisyling.model.protocol.CommonProtocol

/**
 * Created by Emily on 9/30/21
 */
open class BasePresenter(var mBaseView: BaseView) {

    var mProtocol = CommonProtocol()

    var mBaseCallback: BaseProtocol.HttpCallback = object : BaseProtocol.HttpCallback {

        override fun onHttpSuccess(reqType: Int, msg: Message) {
            mBaseView.onHttpSuccess(reqType, msg)
        }

        override fun onHttpError(reqType: Int) {
            mBaseView.onHttpError(reqType)
        }

        override fun onHttpError(reqType: Int, error: String) {
            mBaseView.onHttpError(reqType, error)
        }

        override fun onHttpFailure(reqType: Int, error: String) {
            mBaseView.onHttpFailure(reqType, error)
        }

        override fun onServiceError(reqType: Int) {
            mBaseView.onServiceError(reqType)
        }
    }
}
