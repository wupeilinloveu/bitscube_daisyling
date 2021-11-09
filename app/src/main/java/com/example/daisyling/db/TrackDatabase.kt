package com.example.daisyling.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Emily on 10/26/21
 */
@Database(version = 2, entities = [Track::class])
abstract class TrackDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao

    companion object {
        private var instance: TrackDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): TrackDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(
                context.applicationContext,
                TrackDatabase::class.java, "track_database"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build().apply {
                    instance = this
                }
        }
    }
}