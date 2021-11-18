package com.example.daisyling.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Emily on 10/26/21
 */
@Entity
data class TrackEntity(
    val trackId: String, val artworkUrl100: String?, val trackName: String?,
    val artistName: String?, val trackCensoredName: String?, val previewUrl: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}