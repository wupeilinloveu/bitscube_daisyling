package com.example.daisyling.db

import androidx.room.*

/**
 * Created by Emily on 10/26/21
 */
@Dao
interface MyFavoriteDao {
    @Insert
    fun insertTrack(track: TrackEntity):Long

    @Update
    fun updateTrack(newTrack: TrackEntity)

    @Query("select * from TrackEntity")
    fun loadAllTracks():MutableList<TrackEntity>

    @Query("delete from TrackEntity where trackId=:trackId ")
    fun deleteTrackByTrackId(trackId: String):Int

    @Delete
    fun deleteAllTracks(track: MutableList<TrackEntity>)
}