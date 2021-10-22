package com.example.daisyling.db

import android.content.Context
import com.example.daisyling.R
import com.example.daisyling.common.base.Const
import com.example.daisyling.common.base.MyApp
import org.jetbrains.anko.db.*

/**
 * Created by Emily on 10/19/21
 */
class AnkoSQLiteManager(var ctx: Context = MyApp.instance) {
    fun selectUserByID(user_id: String): User {
        var user: User? = null
        ctx.database.use {
            select(UserTable.TABLE_NAME)
                .whereSimple("${UserTable.USER_ID} = ?", user_id)
                .parseOpt(object : RowParser<User> {
                    override fun parseRow(columns: Array<Any?>): User {
                        val id = columns.get(1)
                        val artworkUrl100 = columns.get(2)
                        val trackName = columns.get(3)
                        val artistName = columns.get(4)
                        val trackCensoredName = columns.get(5)
                        val previewUrl = columns.get(6)
                        user = User(
                            user_id = id.toString(),
                            artworkUrl100 = artworkUrl100.toString(),
                            trackName = trackName.toString(),
                            artistName = artistName.toString(),
                            trackCensoredName = trackCensoredName.toString(),
                            previewUrl = previewUrl.toString()
                        )
                        return user!!
                    }
                })
        }
        return user!!
    }

    fun deleteUser(): Boolean {
        var isDelete: Boolean = false
        ctx.database.use {
            try {
                beginTransaction()
                val result = delete(UserTable.TABLE_NAME) > 0
                if (result) {
                    setTransactionSuccessful()
                    isDelete = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                endTransaction()
            }
        }
        return isDelete
    }

    fun selectAllUsers(): ArrayList<User> {
        val listStr = ArrayList<User>()
        ctx.database.use {
            select(UserTable.TABLE_NAME)
                .parseList(object : MapRowParser<List<User>> {
                    override fun parseRow(columns: Map<String, Any?>): ArrayList<User> {
                        val id = columns.getValue(Const.USER_ID)
                        val artworkUrl100 = columns.getValue(Const.ARTWORK_URL)
                        val trackName = columns.getValue(Const.TRACK_NAME)
                        val artistName = columns.getValue(Const.ARTIST_NAME)
                        val trackCensoredName = columns.getValue(Const.TRACK_CENSORED_NAME)
                        val previewUrl = columns.getValue(Const.PREVIEW_URL)
                        val user = User(
                            user_id = id.toString(),
                            artworkUrl100 = artworkUrl100.toString(),
                            trackName = trackName.toString(),
                            artistName = artistName.toString(),
                            trackCensoredName = trackCensoredName.toString(),
                            previewUrl = previewUrl.toString()
                        )
                        listStr.add(user)
                        return listStr
                    }
                })
        }
        return listStr
    }

    fun insertUser(user: User): Boolean = ctx.database.use {
        try {
            beginTransaction()
            val insertedId = insert(
                UserTable.TABLE_NAME,
                UserTable.USER_ID to user.user_id,
                UserTable.ARTWORKURL to user.artworkUrl100,
                UserTable.TRACKNAME to user.trackName,
                UserTable.ARTISTNAME to user.artistName,
                UserTable.TRACKCENSORENAME to user.trackCensoredName,
                UserTable.PREVIEWURL to user.previewUrl)

            if (insertedId != -1L) {
                setTransactionSuccessful()
                true
            } else {
                false
                throw RuntimeException( MyApp.context?.getString(R.string.fail_to_insert))
            }

        } finally {
            endTransaction()
        }
    }
}