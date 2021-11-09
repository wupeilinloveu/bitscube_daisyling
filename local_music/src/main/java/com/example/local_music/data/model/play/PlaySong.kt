package com.example.local_music.data.model.play

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
 * Created by Emily on 11/1/21
 */
class PlaySong : LitePalSupport {
    var songId: Long = 0

    var songsId: Long = 0

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

    constructor(
        name: String,
        title: String,
        path: String
    ) : this(name, path, 0, path, title, "", "", 0, 0) {
    }

    constructor(
        name: String,
        target: String,
        duration: Int,
        path: String,
        title: String,
        artist: String,
        album: String,
        songId: Long,
        songsId: Long
    ) {
        this.name = name
        this.title = title
        this.target = target
        this.album = album
        this.artist = artist
        this.duration = duration
        this.path = path
        this.songId = songId
        this.songsId = songsId
    }

    fun castSong(): Song {
        return Song(songId, songsId, name, target, duration, path, title, artist, album)
    }

    companion object {
        fun cast(songs: List<Song>): List<PlaySong> {
            var list = arrayListOf<PlaySong>()
            for (song in songs) {
                list.add(song.castSongPlay())
            }
            return list
        }
    }
}