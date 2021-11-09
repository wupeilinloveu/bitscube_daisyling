package com.example.local_music.ui.view.main.song

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.local_music.R
import com.example.local_music.data.base.BaseBean
import com.example.local_music.data.constant.MessageType
import com.example.local_music.data.constant.ShowMsg
import com.example.local_music.ui.adapter.SongsListAdapter
import com.example.local_music.ui.base.BaseFragment
import com.example.local_music.ui.view.main.song.add.AddSongsDialog
import com.example.local_music.ui.view.main.song.edit.SongsEditActivity
import com.example.local_music.util.InjectorUtil
import com.example.local_music.util.RxBus
import com.example.local_music.util.registerInBus
import kotlinx.android.synthetic.main.lm_song_fragment_songs_list.*

/**
 * Created by Emily on 11/1/21
 */
class SongsListFragment : BaseFragment() {
    private lateinit var itemAdapter: SongsListAdapter

    override val layoutId: Int
        get() = R.layout.lm_song_fragment_songs_list

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectorUtil.getSongsModelFactory()
        ).get(SongsListViewModel::class.java)
    }

    override fun initView() {
        initList()
        lm_song_songs_list_add_bt.setOnClickListener { showAddDialog() }
        register()
    }

    override fun initData() {
        viewModel.getSongsList()
        viewModel.dataChanged.observe(this, androidx.lifecycle.Observer {
            itemAdapter.update()
        })
    }

    private fun initList() {
        itemAdapter = SongsListAdapter(viewModel.list)

        itemAdapter.clickListener = {
            var intent = Intent(context, SongListActivity::class.java)
            intent.putExtra("songsId", itemAdapter.getItem(it)?.id)
            startActivity(intent)
        }
        itemAdapter.setMoreClickListener { pos ->
            val id = itemAdapter.getItem(pos)?.id ?: 0
            val pop = activity?.let { view?.let { it1 -> SongsListMorePopup(id, it, it1) } }
            pop?.callback = object : SongsListMorePopup.PopCallBack {
                override fun play() {
                    play(id)
                }

                override fun delete() {
                    delete(id)
                }

                override fun prePlay() {
                    prePlay(id)
                }

                override fun edit() {
                    context?.let { SongsEditActivity.actionStart(it, id) }
                }
            }
            pop?.show()
        }
        lm_song_songs_list.layoutManager = LinearLayoutManager(context)
        lm_song_songs_list.adapter = itemAdapter

    }

    private fun play(songsId: Long) {
        viewModel.play(songsId)
    }


    private fun delete(songsId: Long) {
        if (viewModel.delete(songsId) > 0) {
            showMsg(ShowMsg.msg_delete_ok)
        } else {
            showMsg(ShowMsg.msg_delete_fail)
        }
    }

    private fun prePlay(songsId: Long) {
        viewModel.prePlay(songsId)
        showMsg(ShowMsg.msg_add_playlist_ok)
    }

    private fun showAddDialog() {
        context?.let {
            var dialog = AddSongsDialog(it)
            dialog.dialogResult = object : AddSongsDialog.DialogResult {
                override fun addResult(name: String) {
                    val ret = viewModel.add(name)
                    if (ret) {
                        showMsg("添加成功")
                        dialog.dismiss()
                    } else {
                        showMsg("添加失败")
                    }
                }
            }
            dialog.show()
        }
    }

    companion object {
        fun newInstance(): SongsListFragment {
            return SongsListFragment()
        }
    }

    private fun register() {

        RxBus.observe<BaseBean>()
            .subscribe { t ->
                when (t.messageType) {
                    MessageType.UPDATE_SONGS -> {
                        viewModel.getSongsList()
                    }
                }
            }.registerInBus(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.unRegister(this)
    }
}