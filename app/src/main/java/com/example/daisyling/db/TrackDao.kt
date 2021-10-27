package com.example.daisyling.db

import androidx.room.*

/**
 * Created by Emily on 10/26/21
 */
@Dao
interface TrackDao {
    @Insert
    fun insertTrack(track:Track):Long

    @Update
    fun updateTrack(newTrack:Track)

    @Query("select * from Track")
    fun loadAllTracks():MutableList<Track>

    @Query("delete from Track where trackId=:trackId ")
    fun deleteTrackByTrackId(trackId: String):Int

    @Delete
    fun deleteAllTracks(track: MutableList<Track>)
}