package com.example.local_music.data.model.play

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
 * Created by Emily on 11/1/21
 */
class Song : LitePalSupport {
    var id: Long = 0

    var songs_id: Long = 0

    /**
     * Name
     */
    @Column(nullable = false)
    var name: String

    /**
     * Title
     */
    @Column(nullable = false)
    var title: String

    /**
     * Album
     */
    var album: String

    /**
     * Artist
     */
    var artist: String

    /**
     * Path
     */
    @Column(nullable = false)
    var path: String

    /**
     * Song target
     */
    @Column(nullable = false)
    var target: String

    var duration: Int = 0

    constructor() : this(0, 0, "", "", 0, "", "", "", "") {}

    constructor(
        songs_id: Long,
        name: String,
        title: String,
        path: String
    ) : this(0, songs_id, name, path, 0, path, title, "", "") {
    }

    constructor(
        id: Long,
        songs_id: Long,
        name: String,
        target: String,
        duration: Int,
        path: String,
        title: String,
        artist: String,
        album: String
    ) {
        this.id = id
        this.songs_id = songs_id
        this.name = name
        this.title = title
        this.target = target
        this.album = album
        this.artist = artist
        this.duration = duration
        this.path = path
    }

    fun clone(cloneId: Boolean): Song {
        val newId = if (cloneId) id else 0
        return Song(newId, songs_id, name, target, duration, path, title, artist, album)
    }

    fun castSongPlay(): PlaySong {
        return PlaySong(name, target, duration, path, title, artist, album, id, songs_id)
    }

    companion object {
        fun cast(playSongs: List<PlaySong>): List<Song> {
            val list = arrayListOf<Song>()
            for (song in playSongs) {
                list.add(song.castSong())
            }
            return list
        }
    }
}