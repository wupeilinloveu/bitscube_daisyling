package com.example.local_music.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.local_music.R
import com.example.local_music.data.model.play.Songs

/**
 * Created by Emily on 11/1/21
 */
class AddToSongsListAdapter(val context: Context, private var songList: List<Songs>) :
    BaseAdapter() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    fun upData(songList: List<Songs>) {
        this.songList = songList
        notifyDataSetChanged()
    }

    fun upData() {
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return songList.size
    }

    override fun getItem(position: Int): Any {
        return songList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        var holder: ViewHolder?
        if (view == null) {
            view = mInflater.inflate(R.layout.lm_song_item_song_add_to_songs_list, parent, false)
            holder = ViewHolder()
            holder.name = view.findViewById(R.id.lm_song_item_song_add_to_songs_tv)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }


        holder.name?.text = songList[position].name
        return view!!
    }

    private inner class ViewHolder {
        var name: TextView? = null
    }

}