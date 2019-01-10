package com.example.administrator.starkspireshopping.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper private constructor(context: Context) : SQLiteOpenHelper(context, DatabaseName, null, DatabaseVersion) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        DbTable.onCreate(sqLiteDatabase)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, Old_Ver: Int, New_Ver: Int) {
        DbTable.onUpgrade(sqLiteDatabase, Old_Ver, New_Ver)
    }

    companion object {

        private val DatabaseName = "SparkSpiredb.db"
        private val DatabaseVersion = 1
        private var dbHelper: DbHelper? = null

        @Synchronized
        fun getInstance(context: Context): DbHelper {
            // Use the application context, which will ensure that you
            // don't accidentally leak an Activity's context.
            // See this article for more information: http://bit.ly/6LRzfx
            if (dbHelper == null) {
                dbHelper = DbHelper(context)
            }
            return dbHelper as DbHelper
        }
    }
}
