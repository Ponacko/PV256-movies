package cz.muni.fi.pv256.movio2.uco_433419.data

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import java.util.*


/**
 * @author Tomáš Stolárik <tomas.stolarik@dactylgroup.com>
 */
class FilmProvider : ContentProvider() {
    private var openHelper: FilmDbHelper? = null

    override fun onCreate(): Boolean {
        openHelper = FilmDbHelper(context)
        return true
    }

    override fun query(uri: Uri, projection: Array<String>, selection: String, selectionArgs: Array<String>, sortOrder: String): Cursor {
        Log.d(TAG, Arrays.toString(selectionArgs))
        val retCursor: Cursor
        when (uriMatcher.match(uri)) {
            FILM_ID -> {
                retCursor = openHelper!!.readableDatabase.query(
                        FilmEntry.TABLE_NAME,
                        projection,
                        FilmEntry._ID + " = '" + ContentUris.parseId(uri) + "'", null, null, null,
                        sortOrder
                )
            }
            FILM -> {
                retCursor = openHelper!!.readableDatabase.query(
                        FilmEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs, null, null,
                        sortOrder
                )
            }

            else -> throw UnsupportedOperationException("Unknown uri: " + uri)
        }
        retCursor.setNotificationUri(context!!.contentResolver, uri)
        return retCursor
    }

    override fun getType(uri: Uri): String {
        val match = uriMatcher.match(uri)

        return when (match) {
            FILM -> FilmEntry.CONTENT_TYPE
            FILM_ID -> FilmEntry.CONTENT_ITEM_TYPE
            else -> throw UnsupportedOperationException("Unknown uri: " + uri)
        }
    }

    override fun insert(uri: Uri, values: ContentValues): Uri {
        Log.d(TAG, values.toString())

        val db = openHelper!!.writableDatabase
        val match = uriMatcher.match(uri)
        val returnUri: Uri

        when (match) {
            FILM -> {
                val _id = db.insert(FilmEntry.TABLE_NAME, null, values)
                if (_id > 0)
                    returnUri = FilmEntry.buildFilmUri(_id)
                else
                    throw android.database.SQLException("Failed to insert row into " + uri)
            }
            else -> throw UnsupportedOperationException("Unknown uri: " + uri)
        }
        context!!.contentResolver.notifyChange(uri, null)
        return returnUri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>): Int {
        val db = openHelper!!.writableDatabase
        val match = uriMatcher.match(uri)
        val rowsDeleted: Int
        when (match) {
            FILM -> rowsDeleted = db.delete(FilmEntry.TABLE_NAME, selection, selectionArgs)
            else -> throw UnsupportedOperationException("Unknown uri: " + uri)
        }
        // Because a null deletes all rows
        if (selection == null || rowsDeleted != 0) {
            context!!.contentResolver.notifyChange(uri, null)
        }
        return rowsDeleted
    }

    override fun update(uri: Uri, values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        val db = openHelper!!.writableDatabase
        val match = uriMatcher.match(uri)
        val rowsUpdated: Int

        when (match) {
            FILM -> rowsUpdated = db.update(FilmEntry.TABLE_NAME, values, selection, selectionArgs)
            else -> throw UnsupportedOperationException("Unknown uri: " + uri)
        }
        if (rowsUpdated != 0) {
            context!!.contentResolver.notifyChange(uri, null)
        }
        return rowsUpdated
    }

    companion object {

        private val TAG = FilmProvider::class.java.simpleName

        private val FILM = 100
        private val FILM_ID = 101
        private val uriMatcher = buildUriMatcher()

        private fun buildUriMatcher(): UriMatcher {
            val matcher = UriMatcher(UriMatcher.NO_MATCH)
            val authority = CONTENT_AUTHORITY

            matcher.addURI(authority, PATH_WORK_TIME, FILM)
            matcher.addURI(authority, PATH_WORK_TIME + "/#", FILM_ID)

            return matcher
        }
    }
}