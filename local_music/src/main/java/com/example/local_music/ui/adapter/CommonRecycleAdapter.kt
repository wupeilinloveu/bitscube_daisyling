package com.example.local_music.ui.adapter

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Emily on 11/1/21
 */
abstract class CommonRecycleAdapter<VH : RecyclerView.ViewHolder?, T>(protected var list: MutableList<T>) :
    RecyclerView.Adapter<VH>() {
    override fun getItemCount(): Int {
        return list.size
    }

    var clickListener: ((Int) -> Unit)? = null

    fun addItem(position: Int, data: T): Boolean {
        if (position < list.size && position >= 0) {
            list.add(position, data)
            notifyItemInserted(position)
            return true
        }
        return false
    }

    fun removeItem(position: Int): Boolean {
        if (position < list.size && position >= 0) {
            list.removeAt(position)
            notifyItemRemoved(position)
            return true
        }
        return false
    }

    fun clearAll() {
        list.clear()
        notifyDataSetChanged()
    }

    fun update(list: MutableList<T>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun update() {
        notifyDataSetChanged()
    }

    open fun getItem(position: Int): T {
        return list[position]
    }
}