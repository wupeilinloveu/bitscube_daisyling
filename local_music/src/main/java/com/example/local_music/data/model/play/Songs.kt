package com.example.local_music.data.model.play

import com.example.local_music.data.constant.ConstantParam
import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
 * Created by Emily on 11/1/21
 */
class Songs : LitePalSupport {
    var id: Long

    /**
     * Name
     */
    @Column(nullable = false)
    var name: String

    /**
     * Type
     */
    @Column
    var type: Int = ConstantParam.SONGS_TYPE_SONGS

    /**
     * Song list
     */
    @Column
    var songList: MutableList<Song> = arrayListOf()

    /**
     * Create time
     */
    @Column
    var create_time: Long = System.currentTimeMillis()

    constructor() : this(0, "", ConstantParam.SONGS_TYPE_SONGS) {}

    constructor(
        name: String,
        type: Int
    ) : this(0, name, type) {
    }

    constructor(
        id: Long,
        name: String,
        type: Int
    ) {
        this.id = id
        this.name = name
        this.type = type
    }
}