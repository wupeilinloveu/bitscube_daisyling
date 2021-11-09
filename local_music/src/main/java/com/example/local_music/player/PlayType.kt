package com.example.local_music.player

/**
 * Created by Emily on 11/1/21
 */
enum class PlayType {
    /**
     * Random
     */
//    RANDOM,

    /**
     * List
     */
    LIST,

    /**
     * Single loop
     */
    SINGLE_LOOP,

    /**
     * List loop
     */
    LIST_LOOP;

    fun next(): PlayType {
        val list = values()
        var index = list.indexOf(this)
        return list[++index % list.size]
    }
}