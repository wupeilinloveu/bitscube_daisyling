package com.example.local_music.ui.view.main.play

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import com.example.local_music.R
import com.example.local_music.data.base.BaseBean
import com.example.local_music.data.constant.MessageType
import com.example.local_music.data.model.play.PlaySong
import com.example.local_music.player.PlayManager
import com.example.local_music.ui.adapter.PlayListAdapter
import com.example.local_music.util.RxBus
import com.example.local_music.util.registerInBus
import org.fattili.easypopup.view.EasyPop

/**
 * Created by Emily on 11/1/21
 */
class PlayListPopup : EasyPop {
    constructor(activity: Activity) : super(activity) {}

    var back: ImageView? = null
    var listView: ListView? = null
    var exit: Button? = null
    var noItem: LinearLayout? = null

    var adapter: PlayListAdapter? = null
    var list: List<PlaySong>? = null

    override fun getLayoutId(): Int {
        return R.layout.lm_play_popup_list
    }

    override fun initView(view: View?) {
        back = view?.findViewById(R.id.lm_play_list_back_iv)
        listView = view?.findViewById(R.id.lm_play_list_lv)
        exit = view?.findViewById(R.id.lm_play_list_exit_bt)
        noItem = view?.findViewById(R.id.lm_play_list_empty_ll)
        back?.setOnClickListener { finish() }
        exit?.setOnClickListener { finish() }
        list = PlayManager.getInstance().playList
        adapter = context?.let {
            list?.let { playlist ->
                if (playlist.isNotEmpty()) {
                    noItem?.visibility = View.GONE
                }
                PlayListAdapter(it, playlist)
            } ?: PlayListAdapter(it, arrayListOf())
        }

        listView?.adapter = adapter
        listView?.setOnItemClickListener { _, _, position, _ ->
            PlayManager.getInstance().goto(position)
            finish()
        }
        adapter?.setDeleteListener { pos ->
            PlayManager.getInstance().remove(pos)
            adapter?.upData()
        }
        register()
    }

    override fun initData() {
    }

    override fun onPopDismiss() {
        super.onPopDismiss()
        RxBus.unRegister(this)
    }

    override fun outClickable(): Boolean {
        return false
    }

    private fun register() {
        RxBus.observe<BaseBean>()
            .subscribe { t ->
                when (t.messageType) {
                    MessageType.CHANGE_PLAY_SONGS -> {
                        adapter?.upData()
                    }
                }
            }.registerInBus(this)
    }
}