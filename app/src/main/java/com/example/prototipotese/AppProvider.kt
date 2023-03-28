package com.example.prototipotese

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext

/**
 * Provider for the prototype app.  This is the only class that knows about [AppDatabase].
 *
 *
 *
 */

private const val TAG = "AppProvider"

const val CONTENT_AUTHORITY = "com.example.prototipotese.provider"

private const val HASHES = 100
private const val HASHES_ID = 101

//private const val TIMINGS = 200
//private const val TIMINGS_ID = 201

//private const val STATION_DURATIONS = 400
//private const val STATION_DURATIONS_ID = 401

val CONTENT_AUTHORITY_URI: Uri = Uri.parse("content://$CONTENT_AUTHORITY")


class AppProvider : ContentProvider() {

    private val uriMatcher by lazy { buildUriMatcher() }


    private fun buildUriMatcher(): UriMatcher {
        //Log.d(TAG, "buildUriMatcher: starts")
        val matcher = UriMatcher(UriMatcher.NO_MATCH)

        // e.g. content://learnprogramming.academy.tasktimer.provider/Tasks
        matcher.addURI(CONTENT_AUTHORITY, HashContract.TABLE_NAME, HASHES)

        // e.g. content://learnprogramming.academy.tasktimer.provider/Tasks/8
        matcher.addURI(CONTENT_AUTHORITY, "${HashContract.TABLE_NAME}/#", HASHES_ID)

//        matcher.addURI(CONTENT_AUTHORITY, TimingsContract.TABLE_NAME, TIMINGS);
//        matcher.addURI(CONTENT_AUTHORITY, "${TimingsContract.TABLE_NAME}/#", TIMINGS_ID)

//        matcher.addURI(CONTENT_AUTHORITY, DurationsContract.TABLE_NAME, TASK_DURATIONS);
//        matcher.addURI(CONTENT_AUTHORITY, "${DurationsContract.TABLE_NAME}/#", TASK_DURATIONS_ID)

        return matcher
    }

    override fun onCreate(): Boolean {
        //Log.d(TAG, "onCreate: starts")
        return true
    }

    override fun getType(uri: Uri): String {

        return when (uriMatcher.match(uri)) {
            HASHES -> HashContract.CONTENT_TYPE

            HASHES_ID -> HashContract.CONTENT_ITEM_TYPE

//            TIMINGS -> TimingsContract.CONTENT_TYPE
//
//            TIMINGS_ID -> TimingsContract.CONTENT_ITEM_TYPE

//            TASK_DURATIONS -> DurationsContract.CONTENT_TYPE
//
//            TASK_DURATIONS_ID -> DurationsContract.CONTENT_ITEM_TYPE

            else -> throw IllegalArgumentException("unknown Uri: $uri")
        }
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        //Log.d(TAG, "query: called with uri $uri")
        val match = uriMatcher.match(uri)
        //Log.d(TAG, "query: match is $match")

        val context = requireContext(this)
        val queryBuilder = SQLiteQueryBuilder()

        when (match) {
            HASHES -> queryBuilder.tables = HashContract.TABLE_NAME

            HASHES_ID -> {
                queryBuilder.tables = HashContract.TABLE_NAME
                val taskId = HashContract.getId(uri)
                queryBuilder.appendWhere("${HashContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$taskId")
            }

//            TIMINGS -> queryBuilder.tables = TimingsContract.TABLE_NAME
//
//            TIMINGS_ID -> {
//                queryBuilder.tables = TimingsContract.TABLE_NAME
//                val timingId = TimingsContract.getId(uri)
//                queryBuilder.appendWhere("${TimingsContract.Columns.ID} = ")
//                queryBuilder.appendWhereEscapeString("$timingId")
//            }

//            TASK_DURATIONS -> queryBuilder.tables = DurationsContract.TABLE_NAME
//
//            TASK_DURATIONS_ID -> {
//                queryBuilder.tables = DurationsContract.TABLE_NAME
//                val durationId = DurationsContract.getId(uri)
//                queryBuilder.appendWhere("${DurationsContract.Columns.ID} = ")
//                queryBuilder.appendWhereEscapeString("$durationId")
//            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        val db = AppDatabase.getInstance(context).readableDatabase
        val cursor =
            queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder)
        //Log.d(TAG, "query: rows in returned cursor = ${cursor.count}") // remove this line if you want

        return cursor
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri {
        //Log.d(TAG, "insert: called with uri $uri")
        val match = uriMatcher.match(uri)
        //Log.d(TAG, "insert: match is $match")
        val recordId: Long
        val returnUri: Uri

        val context = requireContext(this)

        when (match) {

            HASHES -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                recordId = db.insert(HashContract.TABLE_NAME, null, values)
                if (recordId != -1L) {
                    returnUri = HashContract.buildUriFromId(recordId)
                } else {
                    throw SQLException("Failed to insert, Uri was $uri")
                }
            }



            else -> throw IllegalArgumentException("Unknown uri: $uri")
        }

        //Log.d(TAG, "Exiting insert, returning $returnUri")
        return returnUri
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        //Log.d(TAG, "update: called with uri $uri")
        val match = uriMatcher.match(uri)
        //Log.d(TAG, "update: match is $match")
        val context = requireContext(this)
        val count: Int
        var selectionCriteria: String

        when (match) {

            HASHES -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                count = db.update(HashContract.TABLE_NAME, values, selection, selectionArgs)
            }

            HASHES_ID -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                val id = HashContract.getId(uri)
                selectionCriteria = "${HashContract.Columns.ID} = $id"

                if (selection != null && selection.isNotEmpty()) {
                    selectionCriteria += " AND ($selection)"
                }

                count =
                    db.update(HashContract.TABLE_NAME, values, selectionCriteria, selectionArgs)
            }


            else -> throw IllegalArgumentException("Unknown uri: $uri")
        }

        //Log.d(TAG, "Exiting update, returning $count")
        return count
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        //Log.d(TAG, "delete: called with uri $uri")
        val match = uriMatcher.match(uri)
        //Log.d(TAG, "delete: match is $match")
        val context = requireContext(this)
        val count: Int
        var selectionCriteria: String

        when (match) {

            HASHES -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                count = db.delete(HashContract.TABLE_NAME, selection, selectionArgs)
            }

            HASHES_ID -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                val id = HashContract.getId(uri)
                selectionCriteria = "${HashContract.Columns.ID} = $id"

                if (selection != null && selection.isNotEmpty()) {
                    selectionCriteria += " AND ($selection)"
                }

                count = db.delete(HashContract.TABLE_NAME, selectionCriteria, selectionArgs)
            }

//            TIMINGS -> {
//                val db = AppDatabase.getInstance(context).writableDatabase
//                count = db.delete(TimingsContract.TABLE_NAME, selection, selectionArgs)
//            }
//
//            TIMINGS_ID -> {
//                val db = AppDatabase.getInstance(context).writableDatabase
//                val id = TimingsContract.getId(uri)
//                selectionCriteria = "${TimingsContract.Columns.ID} = $id"
//
//                if(selection != null && selection.isNotEmpty()) {
//                    selectionCriteria += " AND ($selection)"
//                }
//
//                count = db.delete(TimingsContract.TABLE_NAME, selectionCriteria, selectionArgs)
//            }

            else -> throw IllegalArgumentException("Unknown uri: $uri")
        }

        //Log.d(TAG, "Exiting update, returning $count")
        return count
    }
}