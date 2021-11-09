package com.example.local_music.ui.view.main.song

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.local_music.databinding.LmSongActivitySongListBinding
import com.example.file_select.ChooseFileMultiple
import com.example.local_music.R
import com.example.local_music.data.base.BaseBean
import com.example.local_music.data.constant.MessageType
import com.example.local_music.data.constant.ShowMsg
import com.example.local_music.data.model.play.Songs
import com.example.local_music.ui.adapter.SongListAdapter
import com.example.local_music.ui.base.BaseActivity
import com.example.local_music.ui.view.main.song.add.AddToSongsPopup
import com.example.local_music.ui.view.main.song.edit.SongEditActivity
import com.example.local_music.util.InjectorUtil
import com.example.local_music.util.RxBus
import com.example.local_music.util.registerInBus
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import kotlinx.android.synthetic.main.lm_common_title.*
import kotlinx.android.synthetic.main.lm_song_activity_song_list.*
import java.util.*

/**
 * Created by Emily on 11/1/21
 */
class SongListActivity : BaseActivity() {
    private lateinit var itemAdapter: SongListAdapter
    override val layoutId: Int = R.layout.lm_song_activity_song_list
    override val openBind = true
    private val binding by lazy {
        DataBindingUtil.setContentView<LmSongActivitySongListBinding>(
            this,
            layoutId
        )
    }

    private val viewModel by lazy {
        ViewModelProvider(
            ViewModelStore(),
            InjectorUtil.getSongModelFactory()
        ).get(SongListViewModel::class.java)
    }

    private lateinit var chooseFileMultiple: ChooseFileMultiple

    override fun initView() {
        supportActionBar?.hide()

        binding?.viewModel = viewModel
        setTitleName(getString(R.string.lm_songs_my_list))
        initFileChoice()
        initRcyView()
        lm_common_add_iv.visibility = View.VISIBLE
        lm_common_add_iv.setOnClickListener { view ->
            chooseFileMultiple.popupChoose(
                this@SongListActivity,
                view,
                window,
                layoutInflater,
                true
            )
        }
        register()
    }

    override fun initData() {
        viewModel.songsId = intent.getLongExtra("songsId", 0)
        viewModel.getTitle()?.let { setTitleName(it) }
        viewModel.getSongList()
        viewModel.dataChanged.observe(this, androidx.lifecycle.Observer {
            if (viewModel.list.isEmpty()) {
                lm_song_song_no_item_ll.visibility = View.VISIBLE
                lm_song_song_list.visibility = View.GONE
            } else {
                lm_song_song_no_item_ll.visibility = View.GONE
                lm_song_song_list.visibility = View.VISIBLE
            }
            itemAdapter.update()
            lm_song_song_list.refreshComplete()
        })
    }

    private fun initFileChoice() {
        chooseFileMultiple = ChooseFileMultiple()
        chooseFileMultiple.chooseFileBack = object : ChooseFileMultiple.OnChooseFileBack {
            override fun onChooseBack(paths: ArrayList<String?>?) {
                viewModel.addFile(paths)
            }
        }
        chooseFileMultiple.whiteList = arrayListOf("mp3", "mwa")
    }

    private fun initRcyView() {
        itemAdapter = SongListAdapter(viewModel.list)

        itemAdapter.clickListener = {}
        lm_song_song_list.adapter = itemAdapter

        lm_song_song_list.layoutManager = LinearLayoutManager(this)
        lm_song_song_list.defaultRefreshHeaderView.setRefreshTimeVisible(true)//显示刷新时间
        lm_song_song_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader) //下拉进度条属性
        lm_song_song_list.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader)//上拉进度条属性
        lm_song_song_list.setLoadingMoreEnabled(false)//禁用上拉
        lm_song_song_list.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {}
            override fun onRefresh() {
                //下拉刷新
                try {
                    viewModel.getSongList()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

        itemAdapter.setPlayClickListener { pos ->
            viewModel.play(pos)
        }

        itemAdapter.setMoreClickListener { pos ->
            val id = itemAdapter.getItem(pos)?.id ?: 0
            val pop = SongListMorePopup(this)
            pop.callback = object : SongListMorePopup.PopCallBack {
                override fun delete() {
                    delete(id)
                }

                override fun prePlay() {
                    prePlay(id)
                }

                override fun edit() {
                    SongEditActivity.actionStart(this@SongListActivity, id)
                }

                override fun addToSongs() {
                    addToSongs(id)
                }
            }
            pop.show()
        }
    }

    fun delete(songId: Long) {
        if (viewModel.delete(songId)) {
            showMsg(ShowMsg.msg_delete_ok)
        } else {
            showMsg(ShowMsg.msg_delete_fail)
        }
    }

    fun prePlay(songId: Long) {
        viewModel.prePlay(songId)
        showMsg(ShowMsg.msg_add_playlist_ok)
    }

    private fun addToSongs(songId: Long) {
        val addPop = AddToSongsPopup(viewModel.getSongsList(), this)
        addPop.callback = object : AddToSongsPopup.PopCallBack {
            override fun choice(pos: Int, songs: Songs) {
                val song = viewModel.getSong(songId)?.clone(false)
                if (song != null) {
                    song.songs_id = songs.id
                    if (viewModel.addSong(song)) {
                        showMsg(ShowMsg.msg_add_ok)
                    } else {
                        showMsg(ShowMsg.msg_song_exist)
                    }

                }
            }
        }
        addPop.show()
    }

    private fun register() {
        RxBus.observe<BaseBean>()
            .subscribe { t ->
                when (t.messageType) {
                    MessageType.UPDATE_SONG -> {
                        viewModel.getSongList()
                    }
                    MessageType.CHANGE_PLAY_STATE -> {
                        itemAdapter.notifyDataSetChanged()
                    }
                }
            }.registerInBus(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.unRegister(this)
    }
}