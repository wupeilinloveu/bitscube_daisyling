package com.example.daisyling.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.daisyling.common.base.MyApp
import org.jetbrains.anko.db.*

/**
 * Created by Emily on 10/19/21
 */
class AnkoSQLiteHelper(var ctx: Context = MyApp.instance) : ManagedSQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {
    companion object {
        const val DB_NAME = "mydb2"
        const val DB_VERSION = 1

        val instance by lazy { AnkoSQLiteHelper() }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.createTable(UserTable.TABLE_NAME, true,
            UserTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            UserTable.USER_ID to TEXT,
            UserTable.ARTWORKURL to TEXT,
            UserTable.TRACKNAME to TEXT,
            UserTable.ARTISTNAME to TEXT,
            UserTable.TRACKCENSORENAME to TEXT,
            UserTable.PREVIEWURL to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}

val Context.database: AnkoSQLiteHelper
    get() = AnkoSQLiteHelper.instance