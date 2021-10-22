package com.example.daisyling.common.util

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import scut.carson_ho.searchview.RecordSQLiteOpenHelper

/**
 * Created by Emily on 10/11/21
 */
object SQLiteDatabase {
    private var db: SQLiteDatabase? = null

    /**
     * Query Data , show listView
     */
    fun queryData(context: Context, helper: RecordSQLiteOpenHelper, tempName: String): Cursor{
       return helper.readableDatabase.rawQuery(
            "select id as _id,name from records where name like '%$tempName%' order by id desc ",
            null
        )
    }

    /**
     * Delete Data
     */
    fun deleteData(helper: RecordSQLiteOpenHelper) {
        db = helper.writableDatabase
        db!!.execSQL("delete from records")
        db!!.close()
    }

    /**
     * Search history
     */
    fun hasData(helper: RecordSQLiteOpenHelper,tempName: String): Boolean {
        val cursor: Cursor = helper.readableDatabase.rawQuery(
            "select id as _id,name from records where name =?", arrayOf(tempName)
        )
        return cursor.moveToNext()
    }

    /**
     * Insert Data
     */
    fun insertData(helper: RecordSQLiteOpenHelper, tempName: String) {
        db = helper.writableDatabase
        db!!.execSQL("insert into records(name) values('$tempName')")
        db!!.close()
    }
}