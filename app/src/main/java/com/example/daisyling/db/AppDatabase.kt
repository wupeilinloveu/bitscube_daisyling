package com.example.daisyling.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Emily on 10/26/21
 */
@Database(
    version = AppDatabase.DATABASE_VERSION,
    entities = [TrackEntity::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun myDownLoadDao(): MyDownLoadDao
    abstract fun myFavoriteDao(): MyFavoriteDao
    abstract fun myPlayHistoryDao(): MyPlayHistoryDao

    companion object {
        // 数据库版本
        const val DATABASE_VERSION = 3
        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database"
            ).fallbackToDestructiveMigration()
                .build().apply {
                    instance = this
                }
        }
    }
}