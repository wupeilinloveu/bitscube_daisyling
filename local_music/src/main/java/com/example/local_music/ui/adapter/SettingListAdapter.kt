package com.example.local_music.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.local_music.R

/**
 * Created by Emily on 11/1/21
 */
class SettingListAdapter(data: List<String>) :
    CommonRecycleAdapter<SettingListAdapter.ViewHolder?, String?>(data as MutableList<String?>) {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(context).inflate(R.layout.lm_main_item_setting_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.linearLayout.setOnClickListener {
            if (clickListener != null) {
                clickListener?.invoke(position)
            }
        }
        val info = list[position] ?: return
        holder.tv.text = info
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv: TextView = view.findViewById(R.id.lm_main_item_setting_list_tv)
        var linearLayout: LinearLayout = view.findViewById(R.id.lm_main_item_setting_list_ll)
    }
}