package com.example.prototipotese

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 *
 * Basic database class for the application.
 *
 * The only class that should use this is [AppProvider].
 */

private const val TAG = "AppDatabase"

private const val DATABASE_NAME = "Tese.db"
private const val DATABASE_VERSION = 3

internal class AppDatabase private constructor(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    init {
        //Log.d(TAG, "AppDatabase: initialising")
    }

    override fun onCreate(db: SQLiteDatabase) {
        // CREATE TABLE HASHES (_id INTEGER PRIMARY KEY NOT NULL, Name TEXT NOT NULL, Description TEXT, SortOrder INTEGER);
        //Log.d(TAG, "onCreate: starts")
        val sSQL = """CREATE TABLE ${HashContract.TABLE_NAME} (
            ${HashContract.Columns.ID} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            ${HashContract.Columns.HASH_VALUE} TEXT NOT NULL);""".replaceIndent("")
        //Log.d(TAG, sSQL)
        db.execSQL(sSQL)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //Log.d(TAG, "onUpgrade: starts")
        when(oldVersion) {
            1 -> {
                // upgrade logic from version 1
            }
            else -> throw IllegalStateException("onUpgrade() with unknown newVersion: $newVersion")
        }
    }

    companion object : SingletonHolder<AppDatabase, Context>(::AppDatabase)
//    companion object {
//
//        @Volatile
//        private var instance: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase =
//                instance ?: synchronized(this) {
//                    instance ?: AppDatabase(context).also { instance = it}
//                }
//    }
}